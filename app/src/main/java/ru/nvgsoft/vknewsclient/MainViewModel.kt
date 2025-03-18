package ru.nvgsoft.vknewsclient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.ui.theme.NavigationItem

class MainViewModel : ViewModel() {

    private val initialList = mutableListOf<FeedPost>().apply {
        repeat(20) {
            add(
                FeedPost(id = it)
            )
        }
    }


    private val _selectedNavItem = MutableLiveData<NavigationItem>(NavigationItem.Home)
    val selectedNavItem: LiveData<NavigationItem> = _selectedNavItem

    fun selectNavItem(navigationItem: NavigationItem){
        _selectedNavItem.value = navigationItem
    }

    private val _feedPosts = MutableLiveData<List<FeedPost>>(initialList)
    val feedPosts: LiveData<List<FeedPost>> = _feedPosts

    fun updateCount(feedPost: FeedPost, item: StatisticItem) {
        val oldFeedPosts = _feedPosts.value?.toMutableList() ?: mutableListOf()
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
        _feedPosts.value = oldFeedPosts

    }


    fun remove(feedPost: FeedPost){
        val modifiedList = _feedPosts.value?.toMutableList() ?: mutableListOf()
        modifiedList.remove(feedPost)
        _feedPosts.value = modifiedList
    }

}
