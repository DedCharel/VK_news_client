package ru.nvgsoft.vknewsclient.ui.theme

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(){
    Scaffold(bottomBar = {
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

            items.forEachIndexed{ index, item ->
                NavigationBarItem(
                    selected = selectedItemPosition.value == index ,
                    onClick = { selectedItemPosition.value = index},
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