package ru.nvgsoft.vknewsclient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.nvgsoft.vknewsclient.domain.FeedPost

class CommentsVIewModelFactory(
    private val feedPost: FeedPost
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CommentsViewModel(feedPost) as T
    }
}