package ru.nvgsoft.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.ui.theme.HomeScreenState

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it)
            )
        }
    }

    private val initialState = HomeScreenState.Posts(posts = initialList)

    private val _screenState = MutableLiveData<HomeScreenState>(initialState)
    val screenState: LiveData<HomeScreenState> = _screenState

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val oldFeedPosts = _screenState.value?.toMutableList() ?: mutableListOf()
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

        oldFeedPosts.replaceAll {
            if (it == feedPost) {
                it.copy(statistics = newStatistics)
            } else {
                it
            }
        }
        _screenState.value = oldFeedPosts

    }


    fun remove(feedPost: FeedPost){
        val modifiedList = _screenState.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _screenState.value = modifiedList
    }

}
