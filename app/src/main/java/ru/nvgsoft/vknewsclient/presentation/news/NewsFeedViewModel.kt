package ru.nvgsoft.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vk.id.VKID
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.mapper.NewsFeedMapper
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {



    private val initialState = NewsFeedScreenState.Initial

    private val _screenState = MutableLiveData<NewsFeedScreenState>(initialState)
    val screenState: LiveData<NewsFeedScreenState> = _screenState

    private val mapper = NewsFeedMapper()

    init {
        loadPosts()
        Log.d("NewsFeedViewModel", "Init")
    }

    private fun loadPosts(){
        viewModelScope.launch {
//            val storage = VKPreferencesKeyValueStorage(getApplication())
//            Log.d("NewsFeedViewModel", "token")
//            val token = VKAccessToken.restore(storage)?: return@launch
            val token = VKID.Companion.instance.accessToken?.token
            Log.d("NewsFeedViewModel", "token2")
            val response = ApiFactory.apiService.loadPosts(token.toString())

            val feedPosts = mapper.mapResponseToPost(response)
            _screenState.value = NewsFeedScreenState.Posts(posts = feedPosts)
        }
    }
    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList()
        val oldStatistics = feedPost.statistics.toMutableList()
        val newStatistics = oldStatistics.toMutableList().apply {
            replaceAll { oldItem ->
                if (oldItem.type == item.type) {
                    oldItem.copy(count = oldItem.count + 1)
                } else {
                    oldItem
                }
            }
        }
        val newFeedPost = feedPost.copy(statistics = newStatistics)
        val newPosts = oldPosts.apply {
            replaceAll {
                if (it == feedPost) {
                    newFeedPost
                } else {
                    it
                }
            }
        }
        _screenState.value = NewsFeedScreenState.Posts(newPosts)

    }


    fun remove(feedPost: FeedPost){
        val currentState = screenState.value
        if (currentState !is NewsFeedScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _screenState.value = NewsFeedScreenState.Posts(posts = oldPosts)
    }

}
