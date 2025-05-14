package ru.nvgsoft.vknewsclient.presentation.news

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.usecases.ChangedLikeStatusUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.DeletePostUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.GetPostsUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.LoadNextDataUseCase
import ru.nvgsoft.vknewsclient.extensions.mergeWith
import javax.inject.Inject

class NewsFeedViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase,
    private val loadNextDataUseCase: LoadNextDataUseCase,
    private val deletePostUseCase: DeletePostUseCase,
    private val changedLikeStatusUseCase: ChangedLikeStatusUseCase
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler{_, _ ->
        Log.d("NewsFeedViewModel", "Exception caught by exception handler ")
    }




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
