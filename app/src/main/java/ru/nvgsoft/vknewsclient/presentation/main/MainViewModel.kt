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
import com.vk.api.sdk.VKPreferencesKeyValueStorage
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.id.AccessToken
import com.vk.id.VKID
import com.vk.id.VKIDAuthFail
import com.vk.id.auth.VKIDAuthCallback
import com.vk.id.auth.VKIDAuthParams
import com.vk.id.refresh.VKIDRefreshTokenCallback
import com.vk.id.refresh.VKIDRefreshTokenFail
import kotlinx.coroutines.launch
import ru.nvgsoft.vknewsclient.data.network.ApiFactory

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val _authState = MutableLiveData<AuthState>(AuthState.Initial)
    val authState: LiveData<AuthState> = _authState

    private val vkAuthCallback = object : VKIDAuthCallback{
        override fun onAuth(accessToken: AccessToken) {
            _authState.value = AuthState.Authorized
            Log.d("MainViewModel", "token: ${accessToken.token}")
            saveToken(application, accessToken.token )
            Log.d("MainViewModel", "onAuth")
        }

        override fun onFail(fail: VKIDAuthFail) {
            _authState.value = AuthState.NotAuthorized
            Log.d("MainViewModel", "onFail")
        }
    }

    init {
//        val token = VKID.instance.accessToken?.token
//        Log.d("MainViewModel", "token: $token")
//        if(token != null){
//            _authState.value = AuthState.Authorized
//        }else{
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


//    private val vkRefreshTokenCallback = object : VKIDRefreshTokenCallback {
//        override fun onFail(fail: VKIDRefreshTokenFail) {
//            TODO("Not yet implemented")
//        }
//
//        override fun onSuccess(token: AccessToken) {
//            _authState.value = AuthState.Authorized
//          //  saveToken(application, token.token)
//        }
//    }


    private fun saveToken(context: Context, token: String) {
        val sharedPreferences =
            context.getSharedPreferences("user_preferences", Context.MODE_PRIVATE)
        sharedPreferences.edit() { putString("user_token", token) }
    }

    companion object {
        private const val VK_SCOPE_WALL = "wall"
        private const val VK_SCOPE_FRIENDS = "friends"
    }

}

