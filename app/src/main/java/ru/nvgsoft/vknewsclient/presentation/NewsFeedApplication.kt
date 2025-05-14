package ru.nvgsoft.vknewsclient.presentation

import android.app.Application
import ru.nvgsoft.vknewsclient.di.ApplicationComponent
import ru.nvgsoft.vknewsclient.di.DaggerApplicationComponent
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost

class NewsFeedApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(
            this,
            FeedPost(0,0, "", "","","", "", listOf(), false)
        )
    }
}