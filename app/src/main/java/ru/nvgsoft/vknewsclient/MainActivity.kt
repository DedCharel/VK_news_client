package ru.nvgsoft.vknewsclient

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            VkNewsClientTheme {
//                // A surface container using the 'background' color from the theme
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background)
//                        .padding(8.dp),
//                ) {
//                    PostCard()
//                }
//            }
            Test2()
        }
    }
}

@Composable
private fun Test() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Example3()
    }
}

@Composable
private fun Example1() {
    OutlinedButton(onClick = { /*TODO*/ }, shape = RoundedCornerShape(4.dp)) {
        Text(text = "Hello world")
    }
}

@Composable
private fun Example2() {
    TextField(
        value = "Value",
        onValueChange = {},
        label = { Text(text = "Label") },
    )
}

@Composable
private fun Example3() {
    val openAlertDialog = remember {
        mutableStateOf(true)
    }

    when {
        openAlertDialog.value -> {
            AlertDialog(
                onDismissRequest = { openAlertDialog.value = false },
                confirmButton = {
                    Text(text = "Yes")
                },
                dismissButton = {
                    Text(text = "No")
                },
                title = {
                    Text(text = "Are you sure?")
                },
                text = {
                    Text(text = "Do you want to delete this file?")
                },
            )
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Test2() {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                NavigationDrawerItem(
                    label = { Text(text = " Text 1") },
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Filled.Call, contentDescription = null) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        NavigationDrawerItem(
                            label = { Text(text = " Text 2") },
                            selected = true,
                            onClick = {},
                            icon = { Icon(Icons.Filled.Email, contentDescription = null) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        NavigationDrawerItem(
                            label = { Text(text = " Text 3") },
                            selected = true,
                            onClick = {},
                            icon = { Icon(Icons.Filled.Place, contentDescription = null) }
                        )
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = "Another simple text for show test!")
                    }
            }
            ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(text = "TopAppBar title")
                        },
                        navigationIcon = {
                            IconButton(onClick = {}) {
                                Icon(Icons.Filled.Menu, contentDescription = null)
                            }
                        }
                    )
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            icon = {
                                Icon(Icons.Outlined.Edit, contentDescription = null)
                            },
                            label = {
                                Text(text = "Edit")
                            }
                        )
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            icon = {
                                Icon(Icons.Filled.Favorite, contentDescription = null)
                            },
                            label = {
                                Text(text = "Favorite")
                            }
                        )
                        NavigationBarItem(
                            selected = true,
                            onClick = {},
                            icon = {
                                Icon(Icons.Outlined.Delete, contentDescription = null)
                            },
                            label = {
                                Text(text = "Delete")
                            }
                        )
                    }
                }
            ) {
                Text(
                    modifier = Modifier.padding(it),
                    text = "This is scaffold content",
                )
            }
        }

}

