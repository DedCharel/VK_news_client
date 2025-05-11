package ru.nvgsoft.vknewsclient.presentation.news

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.usecases.ChangedLikeStatusUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.DeletePostUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.GetPostsUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.LoadNextDataUseCase
import ru.nvgsoft.vknewsclient.extensions.mergeWith

class NewsFeedViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler{_, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exception handler ")
    }
    private val repository = NewsFeedRepositoryImpl(application)

    private val getPostsUseCase = GetPostsUseCase(repository)
    private val loadNextDataUseCase = LoadNextDataUseCase(repository)
    private val deletePostUseCase = DeletePostUseCase(repository)
    private val changedLikeStatusUseCase = ChangedLikeStatusUseCase(repository)

    private val postsFlow = getPostsUseCase()


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
            loadNextDataUseCase()
        }
    }

    fun changeLikeStatus(feedPost: FeedPost){
        viewModelScope.launch(exceptionHandler) {
            changedLikeStatusUseCase(feedPost)
        }

    }

    fun remove(feedPost: FeedPost){
        viewModelScope.launch(exceptionHandler) {
            deletePostUseCase(feedPost)
        }

    }

}
