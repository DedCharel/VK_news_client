package ru.nvgsoft.vknewsclient.domain.usecases

import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository
import javax.inject.Inject

class LoadNextDataUseCase @Inject constructor(
    private val repository: NewsFeedRepository
) {
    suspend operator fun invoke(){
        repository.loadNexData()
    }
}