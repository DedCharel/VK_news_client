package ru.nvgsoft.vknewsclient.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.data.network.ApiService
import ru.nvgsoft.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.nvgsoft.vknewsclient.domain.repository.NewsFeedRepository

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindRepository(impl: NewsFeedRepositoryImpl): NewsFeedRepository

    companion object{
        @ApplicationScope
        @Provides
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }
    }
}