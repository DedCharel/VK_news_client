package ru.nvgsoft.vknewsclient.domain.repository

import kotlinx.coroutines.flow.StateFlow
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.entity.PostComment
import ru.nvgsoft.vknewsclient.domain.entity.AuthState

interface NewsFeedRepository {



    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changedLikeStatus(feedPost: FeedPost)

    fun getPosts(): StateFlow<List<FeedPost>>

    fun getAuthState(): StateFlow<AuthState>

    suspend fun loadNexData()

    suspend fun checkAuth()


}