package ru.nvgsoft.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()
    val fabIsVisible = rememberSaveable {
        mutableStateOf(true)
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        floatingActionButton = {
            if (fabIsVisible.value){
                FloatingActionButton(
                    onClick = {
                        scope.launch {
                            val action = snackbarHostState.showSnackbar(
                                "This is snackbar",
                                actionLabel = "Hide FAB",
                                duration = SnackbarDuration.Long
                            )
                            if (action == SnackbarResult.ActionPerformed){
                                fabIsVisible.value = false
                            }
                        }
                    }) {
                    Icon(imageVector = Icons.Filled.Favorite, contentDescription = null)
                }
            }

        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                val selectedItemPosition = remember {
                    mutableStateOf(0)
                }
                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemPosition.value == index,
                        onClick = { selectedItemPosition.value = index },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }
                    )
                }
            }
        }) {

    }
}