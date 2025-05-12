package ru.nvgsoft.vknewsclient.presentation.main

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.core.content.edit
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import com.vk.id.refreshuser.VKIDGetUserCallback
import com.vk.id.refreshuser.VKIDGetUserFail
import com.vk.id.refreshuser.VKIDGetUserParams
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.network.ApiFactory
import ru.nvgsoft.vknewsclient.data.repository.NewsFeedRepositoryImpl
import ru.nvgsoft.vknewsclient.domain.usecases.CheckAuthUseCase
import ru.nvgsoft.vknewsclient.domain.usecases.GetAuthStateUseCase

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val repository = NewsFeedRepositoryImpl(application)
    private val checkAuthUseCase = CheckAuthUseCase(repository)
    private val getAuthStateUseCase = GetAuthStateUseCase(repository)
    val authState = getAuthStateUseCase()

    init {
        viewModelScope.launch {
            checkAuthUseCase()

        }

    }






}

