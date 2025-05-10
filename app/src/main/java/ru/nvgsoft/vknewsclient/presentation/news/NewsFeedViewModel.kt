package ru.nvgsoft.vknewsclient.presentation.news

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.repository.NewsFeedRepository
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.extensions.mergeWith

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = NewsFeedRepository(application)
    private val postsFlow = repository.posts

    private val loadingNextDataEvent = MutableSharedFlow<Unit>()
    private val loadNextDataFlow=   flow {
        loadingNextDataEvent.collect{
            emit(
                NewsFeedScreenState.Posts(
                    posts = postsFlow.value,
                    nextDataIsLoading = true
                )
            )
        }
    }


    val screenState = postsFlow
        .filter { it.isNotEmpty() }
        .map { NewsFeedScreenState.Posts(posts = it) as NewsFeedScreenState}
        .onStart { emit(NewsFeedScreenState.Loading) }
        .mergeWith(loadNextDataFlow)


    fun loadNextPosts(){

        viewModelScope.launch {
            loadingNextDataEvent.emit(Unit)
            repository.loadNexData()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch {
            repository.changedLikeStatus(feedPost)
        }

    }

    fun remove(feedPost: FeedPost){
        viewModelScope.launch {
            repository.deletePost(feedPost)
        }

    }

}
