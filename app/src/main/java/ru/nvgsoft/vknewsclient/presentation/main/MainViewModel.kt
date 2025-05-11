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
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.network.ApiFactory

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    init {
        viewModelScope.launch {
            VKID.instance.getUserData(
                callback = object : VKIDGetUserCallback {
                    override fun onSuccess(user: VKIDUser) {
                        _authState.value = AuthState.Authorized
                    }
                    override fun onFail(fail: VKIDGetUserFail) {
                        when (fail) {
                            is VKIDGetUserFail.FailedApiCall -> vkAuthorize()
                            is VKIDGetUserFail.IdTokenTokenExpired ->   refreshToken()
                            is VKIDGetUserFail.NotAuthenticated ->   vkAuthorize()
                        }
                    }
                }
            )

        }

    }

    private val vkAuthCallback = object : VKIDAuthCallback {
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Authorized
            Log.d("MainViewModel", "token: ${accessToken.token}")
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
            Log.d("MainViewModel", "onFail")
        }
    }

    private fun vkAuthorize() {
        viewModelScope.launch {
            Log.d("MainViewModel", "Init")
            VKID.instance.authorize(
                callback = vkAuthCallback,
                params = VKIDAuthParams {
                    scopes = setOf(VK_SCOPE_WALL, VK_SCOPE_FRIENDS)
                }
            )
            Log.d("MainViewModel", "InitEnd")
        }
        //     }
    }


    private fun refreshToken() {
        viewModelScope.launch {
            VKID.instance.refreshToken(
                callback = object : VKIDRefreshTokenCallback {
                    override fun onSuccess(token: AccessToken) {
                        _authState.value = AuthState.Authorized
                    }

                    override fun onFail(fail: VKIDRefreshTokenFail) {
                        when (fail) {
                            is VKIDRefreshTokenFail.FailedApiCall -> vkAuthorize()
                            is VKIDRefreshTokenFail.FailedOAuthState -> vkAuthorize()
                            is VKIDRefreshTokenFail.RefreshTokenExpired -> vkAuthorize()
                            is VKIDRefreshTokenFail.NotAuthenticated -> vkAuthorize()
                        }
                    }
                }
            )
        }

    }


   companion object {
        private const val VK_SCOPE_WALL = "wall"
        private const val VK_SCOPE_FRIENDS = "friends"
    }

}

