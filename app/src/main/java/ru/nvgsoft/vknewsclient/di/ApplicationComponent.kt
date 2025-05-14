package ru.nvgsoft.vknewsclient.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost
import ru.nvgsoft.vknewsclient.presentation.main.MainActivity
import javax.inject.Inject

@ApplicationScope
@Component(modules = [
    DataModule::class,
    ViewModelModule::class
])
interface ApplicationComponent {

    fun inject(mainActivity: MainActivity)


    @Component.Factory
    interface Factory{

        fun create(
            @BindsInstance context: Context,
            @BindsInstance feedPost: FeedPost
        ):ApplicationComponent
    }
}