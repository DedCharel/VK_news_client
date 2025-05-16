package ru.nvgsoft.vknewsclient.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.vk.id.VKID
import ru.nvgsoft.vknewsclient.domain.entity.AuthState
import ru.nvgsoft.vknewsclient.presentation.NewsFeedApplication
import ru.nvgsoft.vknewsclient.presentation.ViewModelFactory
import ru.nvgsoft.vknewsclient.presentation.getApplicationComponent
import ru.nvgsoft.vknewsclient.ui.theme.VkNewsClientTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        VKID.init(this)

        setContent {
            val component = getApplicationComponent()
            val viewModel: MainViewModel = viewModel(factory = component.getViewModelFactory())
            val authState = viewModel.authState.collectAsState(AuthState.Initial)
            Log.d("VKMainActivity", "state")

            VkNewsClientTheme {

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