package ru.nvgsoft.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.PostComment
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {


    private val comments = mutableListOf<PostComment>().apply {
        repeat(10){
            add(
                PostComment(id = it)
            )
        }
    }
    private val sourceList = mutableListOf<FeedPost>().apply {
        repeat(10) {
            add(FeedPost(id = it))
        }
    }
    private val initialState = HomeScreenState.Posts(posts = sourceList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    private var savedState: HomeScreenState? = initialState

    fun showComments(feedPost: FeedPost) {
        savedState = _screenState.value
        _screenState.value = HomeScreenState.Comments(feedPost = feedPost, comments = comments)
    }

    fun closeComments() {

        _screenState.value = savedState ?: throw NullPointerException()
    }

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
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
        _screenState.value = HomeScreenState.Posts(newPosts)

    }


    fun remove(feedPost: FeedPost){
        val currentState = screenState.value
        if (currentState !is HomeScreenState.Posts) return
        val oldPosts = currentState.posts.toMutableList() ?: mutableListOf()
        oldPosts.remove(feedPost)
        _screenState.value = HomeScreenState.Posts(posts = oldPosts)
    }

}
