package ru.nvgsoft.vknewsclient.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.nvgsoft.vknewsclient.presentation.comments.CommentsViewModel

@Module
interface CommentsViewModelModule {

    @IntoMap
    @ViewModelKey(CommentsViewModel::class)
    @Binds
    fun bindCommentsViewModel(viewModel: CommentsViewModel): ViewModel
}