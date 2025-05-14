package ru.nvgsoft.vknewsclient.data.repository

import android.app.Application
import android.util.Log
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import com.vk.id.refreshuser.VKIDGetUserCallback
import com.vk.id.refreshuser.VKIDGetUserFail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.mapper.NewsFeedMapper
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.data.network.ApiService
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.entity.PostComment
import ru.nvgsoft.vknewsclient.domain.entity.StatisticItem
import ru.nvgsoft.vknewsclient.domain.entity.StatisticType
import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository
import ru.nvgsoft.vknewsclient.extensions.mergeWith
import ru.nvgsoft.vknewsclient.domain.entity.AuthState
import javax.inject.Inject

class NewsFeedRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: NewsFeedMapper
) : NewsFeedRepository {

    val authState = MutableStateFlow<AuthState>(AuthState.Initial)

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val nextDataNeededEvents = MutableSharedFlow<Unit>(replay = 1)
    private val refreshedListFlow = MutableSharedFlow<List<FeedPost>>()
    private val loadedListFlow = flow {
        nextDataNeededEvents.emit(Unit)
        nextDataNeededEvents.collect {
            val startFrom = nextFrom
            if (startFrom == null && feedPosts.isNotEmpty()) {
                emit(feedPosts)
                return@collect
            }

            val response = if (startFrom == null) {
                apiService.loadPosts(getAccessToken())
            } else {
                apiService.loadPosts(getAccessToken(), startFrom)
            }
            nextFrom = response.newsFeedContent.nextFrom
            val posts = mapper.mapResponseToPost(response)
            _feedPosts.addAll(posts)
            emit(feedPosts)
        }
    }.retry {
        delay(RETRY_TIMEOUT_MILES)
        true
    }


    private val _feedPosts = mutableListOf<FeedPost>()
    private val feedPosts: List<FeedPost>
        get() = _feedPosts.toList()

    private var nextFrom: String? = null

    private val posts: StateFlow<List<FeedPost>> = loadedListFlow
        .mergeWith(refreshedListFlow)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Lazily,
            initialValue = feedPosts
        )

    override fun getPosts(): StateFlow<List<FeedPost>> = posts

    override suspend fun loadNexData() {
        nextDataNeededEvents.emit(Unit)
    }

    override fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>> = flow {
        val response = apiService.getComments(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            postId = feedPost.id
        )
        val result = mapper.mupResponseToComments(response)
        emit(result)
    }.retry {
        delay(RETRY_TIMEOUT_MILES)
        true
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.Lazily,
        initialValue = listOf()
    )

    private fun getAccessToken(): String {
        // return token?.accessToken ?: throw IllegalStateException("toke is null")
        return VKID.Companion.instance.accessToken?.token
            ?: throw IllegalStateException("toke is null")
    }

    override suspend fun deletePost(feedPost: FeedPost) {
        apiService.ignorePost(
            token = getAccessToken(),
            ownerId = feedPost.communityId,
            itemId = feedPost.id
        )
        _feedPosts.remove(feedPost)
        refreshedListFlow.emit(feedPosts)
    }

    override suspend fun changedLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked) {
            apiService.deleteLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        } else {
            apiService.addLike(
                token = getAccessToken(),
                ownerId = feedPost.communityId,
                itemId = feedPost.id
            )
        }
        val newLikesCount = response.likes.count
        val newStatistics = feedPost.statistics.toMutableList().apply {
            removeIf { it.type == StatisticType.LIKES }
            add(StatisticItem(type = StatisticType.LIKES, newLikesCount))
        }
        val newPost = feedPost.copy(statistics = newStatistics, isLiked = !feedPost.isLiked)
        val postIndex = _feedPosts.indexOf(feedPost)
        _feedPosts[postIndex] = newPost
        refreshedListFlow.emit(feedPosts)
    }

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            authState.value = AuthState.Authorized
            Log.d("MainViewModel", "token: ${accessToken.token}")
        }

        override fun onFail(fail: VKIDAuthFail) {
            authState.value = AuthState.NotAuthorized
            Log.d("MainViewModel", "onFail")
        }
    }

    override fun getAuthState(): StateFlow<AuthState> {
        return authState
    }

    override suspend fun checkAuth() {
        VKID.instance.getUserData(
            callback = object : VKIDGetUserCallback {
                override fun onSuccess(user: VKIDUser) {
                    authState.value = AuthState.Authorized
                }

                override fun onFail(fail: VKIDGetUserFail) {
                    when (fail) {
                        is VKIDGetUserFail.FailedApiCall -> vkAuthorize()
                        is VKIDGetUserFail.IdTokenTokenExpired -> refreshToken()
                        is VKIDGetUserFail.NotAuthenticated -> vkAuthorize()
                    }
                }
            }
        )
    }

    private fun vkAuthorize() {
        coroutineScope.launch {
            VKID.instance.authorize(
                callback = vkAuthCallback,
                params = VKIDAuthParams {
                    scopes = setOf(VK_SCOPE_WALL, VK_SCOPE_FRIENDS)
                }
            )
        }

    }


    private fun refreshToken() {
        coroutineScope.launch {
            VKID.instance.refreshToken(
                callback = object : VKIDRefreshTokenCallback {
                    override fun onSuccess(token: AccessToken) {
                        authState.value = AuthState.Authorized
                    }

                    override fun onFail(fail: VKIDRefreshTokenFail) {
                        when (fail) {
                            is VKIDRefreshTokenFail.FailedApiCall -> vkAuthorize()
                            is VKIDRefreshTokenFail.FailedOAuthState -> vkAuthorize()
                            is VKIDRefreshTokenFail.RefreshTokenExpired -> vkAuthorize()
                            is VKIDRefreshTokenFail.NotAuthenticated -> vkAuthorize()
                        }
                    }
                }
            )
        }

    }

    companion object {
        private const val RETRY_TIMEOUT_MILES = 3000L
        private const val VK_SCOPE_WALL = "wall"
        private const val VK_SCOPE_FRIENDS = "friends"
    }

}