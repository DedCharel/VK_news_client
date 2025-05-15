package ru.nvgsoft.vknewsclient.di

import dagger.BindsInstance
import dagger.Subcomponent
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.presentation.ViewModelFactory

@Subcomponent(modules = [CommentsViewModelModule::class])
interface CommentsScreenComponent {

    fun getViewModelFactory(): ViewModelFactory

    @Subcomponent.Factory
    interface Factory{

        fun create(
            @BindsInstance feedPost: FeedPost
        ): CommentsScreenComponent
    }
}