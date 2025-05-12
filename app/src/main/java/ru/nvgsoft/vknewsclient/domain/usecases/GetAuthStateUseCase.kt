package ru.nvgsoft.vknewsclient.domain.usecases

import kotlinx.coroutines.flow.StateFlow
import ru.nvgsoft.vknewsclient.domain.entity.AuthState
import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository

class GetAuthStateUseCase(
    private val repository: NewsFeedRepository
) {
    operator fun invoke(): StateFlow<AuthState>{
        return repository.getAuthState()
    }
}