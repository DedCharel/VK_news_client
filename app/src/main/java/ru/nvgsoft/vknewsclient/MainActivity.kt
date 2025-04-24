package ru.nvgsoft.vknewsclient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAuthenticationResult
import com.vk.api.sdk.auth.VKScope
import com.vk.id.VKID
import ru.nvgsoft.vknewsclient.ui.theme.ActivityResultTest
import ru.nvgsoft.vknewsclient.ui.theme.AuthState
import ru.nvgsoft.vknewsclient.ui.theme.LoginScreen
import ru.nvgsoft.vknewsclient.ui.theme.MainScreen
import ru.nvgsoft.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VKID.init(this)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.observeAsState(AuthState.Initial)

                when(authState.value){
                    is AuthState.Authorized -> {
                        MainScreen()
                    }
                    is AuthState.NotAuthorized ->{
                        LoginScreen(this, viewModel)
                    }
                    else -> {

                    }
                }

            }

        }
    }
}


