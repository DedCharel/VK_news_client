package ru.nvgsoft.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nvgsoft.vknewsclient.presentation.comments.CommentsViewModel
import ru.nvgsoft.vknewsclient.presentation.main.MainViewModel
import ru.nvgsoft.vknewsclient.presentation.news.NewsFeedViewModel

@Module
interface ViewModelModule {

    @IntoMap
    @ViewModelKey(NewsFeedViewModel::class)
    @Binds
    fun bindNewsFeedViewModel(viewModel: NewsFeedViewModel): ViewModel

    @IntoMap
    @ViewModelKey(MainViewModel::class)
    @Binds
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel


}