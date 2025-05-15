package ru.nvgsoft.vknewsclient.presentation

import android.app.Application
import ru.nvgsoft.vknewsclient.di.ApplicationComponent
import ru.nvgsoft.vknewsclient.di.DaggerApplicationComponent

class NewsFeedApplication: Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}