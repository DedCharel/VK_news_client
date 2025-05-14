package ru.nvgsoft.vknewsclient.domain.usecases

import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class ChangedLikeStatusUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(feedPost: FeedPost){
        repository.changedLikeStatus(feedPost)
    }
}