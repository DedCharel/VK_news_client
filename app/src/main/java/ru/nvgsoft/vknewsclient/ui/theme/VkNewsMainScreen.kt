package ru.nvgsoft.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nvgsoft.vknewsclient.MainViewModel
import ru.nvgsoft.vknewsclient.domain.StatisticItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val selectedItemPosition by viewModel.selectedNavItem.observeAsState(NavigationItem.Home)
    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                val items = listOf(
                    NavigationItem.Home,
                    NavigationItem.Favourite,
                    NavigationItem.Profile
                )

                items.forEach() { item ->
                    NavigationBarItem(
                        selected = selectedItemPosition == item,
                        onClick = { viewModel.selectNavItem(item) },
                        icon = {
                            Icon(item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }
                    )
                }
            }
        }) { paddingValues ->
        when (selectedItemPosition) {
            NavigationItem.Home -> {
                HomeScreen(viewModel = viewModel, paddingValues = paddingValues)
            }

            NavigationItem.Favourite -> TextCounter(name = "Favorite")
            NavigationItem.Profile -> TextCounter(name = "Profile")
        }
    }


}

@Composable
private fun TextCounter(name: String) {
    var count by remember {
        mutableStateOf(0)
    }

    Text(
        modifier = Modifier.clickable { count++ },
        text = "$name Count: $count",
        color = Color.Black
    )
}