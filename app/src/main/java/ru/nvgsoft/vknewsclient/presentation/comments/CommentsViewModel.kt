package ru.nvgsoft.vknewsclient.presentation.comments

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.map
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.usecases.GetCommentsUseCase
import javax.inject.Inject

class CommentsViewModel @Inject constructor(
    private val feedPost: FeedPost,
    private val getCommentsUseCase: GetCommentsUseCase
): ViewModel() {


    val screenState = getCommentsUseCase(feedPost)
        .map {
            CommentsScreenState.Comments(
                feedPost = feedPost,
                comments = it
            )
        }

}