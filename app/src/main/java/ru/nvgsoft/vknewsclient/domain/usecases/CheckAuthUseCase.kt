package ru.nvgsoft.vknewsclient.domain.usecases

import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository

class CheckAuthUseCase(
    private val repository: NewsFeedRepository
) {

     suspend operator fun invoke() {
         repository.checkAuth()
     }
}