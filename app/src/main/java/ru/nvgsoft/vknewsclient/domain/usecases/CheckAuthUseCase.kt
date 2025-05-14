package ru.nvgsoft.vknewsclient.domain.usecases

import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class CheckAuthUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {

     suspend operator fun invoke() {
         repository.checkAuth()
     }
}