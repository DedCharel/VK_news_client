package ru.nvgsoft.vknewsclient.data.repository

import android.app.Application
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.id.VKID
import ru.nvgsoft.vknewsclient.data.mapper.NewsFeedMapper
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.domain.StatisticType

class NewsFeedRepository(application: Application) {

    val storage = VKPreferencesKeyValueStorage(application)
    val token = VKAccessToken.restore(storage)

    private val apiService = ApiFactory.apiService
    private val mapper = NewsFeedMapper()

    private val _feedPosts = mutableListOf<FeedPost>()
    val feedPost: List<FeedPost>
        get() = _feedPosts.toList()

    suspend fun loadRecommendation(): List<FeedPost> {
        val response = apiService.loadPosts(getAccessToken())
        val posts = mapper.mapResponseToPost(response)
        _feedPosts.addAll(posts)
        return posts
    }

    private fun getAccessToken(): String {
        // return token?.accessToken ?: throw IllegalStateException("toke is null")
        return VKID.Companion.instance.accessToken?.token
            ?: throw IllegalStateException("toke is null")
    }

    suspend fun changedLikeStatus(feedPost: FeedPost) {
        val response = if (feedPost.isLiked){
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
    }


}