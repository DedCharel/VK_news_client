package ru.nvgsoft.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class GetPostsUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<List<FeedPost>>{
        return repository.getPosts()
    }
}