package ru.nvgsoft.vknewsclient.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKKeyValueStorage
import com.vk.id.VKID
import com.vk.id.VKIDUser
import com.vk.id.auth.VKIDAuthParams
import ru.nvgsoft.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VKID.init(this)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)
                Log.d("VKMainActivity", "state")

                when (authState.value) {
                    is AuthState.Authorized -> {
                        Log.d("VkMainActivity", "Auth")
                        MainScreen()
                    }

                    is AuthState.NotAuthorized -> {
                        Log.d("VkMainActivity", "NotAuth")
                    }

                    else -> {
                        Log.d("VKMainActivity", "else")
                    }
                }

            }

        }
    }
}