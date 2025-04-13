package ru.nvgsoft.vknewsclient.ui.theme

import ru.nvgsoft.vknewsclient.domain.FeedPost

sealed class NewsFeedScreenState {

    object Initial:NewsFeedScreenState()

    data class Posts(val posts: List<FeedPost>) : NewsFeedScreenState()

}