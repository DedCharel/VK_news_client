package ru.nvgsoft.vknewsclient.ui.theme

import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.PostComment

sealed class HomeScreenState {

    object Initial:HomeScreenState()

    data class Posts(val posts: List<FeedPost>) : HomeScreenState()

    data class Comments(val feedPost: FeedPost, val comments: List<PostComment>) : HomeScreenState()
}