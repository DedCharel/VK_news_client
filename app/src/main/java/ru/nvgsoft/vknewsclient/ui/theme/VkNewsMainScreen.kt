package ru.nvgsoft.vknewsclient.ui.theme

import android.annotation.SuppressLint
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.nvgsoft.vknewsclient.MainViewModel
import ru.nvgsoft.vknewsclient.domain.StatisticItem

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(viewModel: MainViewModel) {

    Scaffold(
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
        }) {paddingValues ->
        val feedPosts = viewModel.feedPosts.observeAsState(listOf())
        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            items(feedPosts.value, key = { it.id }) { feedPost ->
                val dismissThresholds = with(receiver = LocalDensity.current) {
                    LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.5F
                }
                val dismissState = SwipeToDismissBoxState(
                    initialValue = SwipeToDismissBoxValue.Settled,
                    density = LocalDensity.current,
                    confirmValueChange = { value ->
                        val isDismissed = value in setOf(
                            SwipeToDismissBoxValue.EndToStart,
                        )
                        if (isDismissed) {
                            viewModel.remove(feedPost)
                            true
                        }
                        false
                    },
                    positionalThreshold = { dismissThresholds }
                )
                SwipeToDismissBox(
                    modifier = Modifier.animateItem(),
                    state = dismissState,
                    backgroundContent = {}) {
                    PostCard(
                        modifier = Modifier.padding(8.dp),
                        feedPost = feedPost,
                        onLikeClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onShareClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onViewsClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                        onCommentClickListener = { statisticItem ->
                            viewModel.updateCount(feedPost, statisticItem)
                        },
                    )
                }


            }
        }


    }
}