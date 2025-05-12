package ru.nvgsoft.vknewsclient.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.id.VKID
import ru.nvgsoft.vknewsclient.domain.entity.AuthState
import ru.nvgsoft.vknewsclient.ui.theme.VkNewsClientTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        VKID.init(this)

        setContent {
            VkNewsClientTheme {
                val viewModel: MainViewModel = viewModel()
                val authState = viewModel.authState.collectAsState(AuthState.Initial)
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