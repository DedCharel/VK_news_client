package ru.nvgsoft.vknewsclient.domain.repository

import android.app.Application
import com.vk.id.VKID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.stateIn
import ru.nvgsoft.vknewsclient.data.mapper.NewsFeedMapper
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.entity.PostComment
import ru.nvgsoft.vknewsclient.domain.entity.StatisticItem
import ru.nvgsoft.vknewsclient.domain.entity.StatisticType
import ru.nvgsoft.vknewsclient.extensions.mergeWith

interface NewsFeedRepository {

    fun getPosts(): StateFlow<List<FeedPost>>

    fun getComments(feedPost: FeedPost): StateFlow<List<PostComment>>

    suspend fun loadNexData()

    suspend fun deletePost(feedPost: FeedPost)

    suspend fun changedLikeStatus(feedPost: FeedPost)

}