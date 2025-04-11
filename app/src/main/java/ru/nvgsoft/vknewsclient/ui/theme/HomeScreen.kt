package ru.nvgsoft.vknewsclient.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ru.nvgsoft.vknewsclient.MainViewModel
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.PostComment

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    paddingValues: PaddingValues
) {
    val feedPosts = viewModel.feedPosts.observeAsState(listOf())

    if (feedPosts.value.isNotEmpty()){
        val comments = mutableListOf<PostComment>().apply {
            repeat(20){
                add(
                    PostComment(id = it)
                )

            }
        }
        CommentsScreen(feedPost = feedPosts.value.get(0), comments = comments)
    }
//    LazyColumn(
//        modifier = Modifier.padding(paddingValues),
//        verticalArrangement = Arrangement.spacedBy(5.dp)
//    ) {
//        items(feedPosts.value, key = { it.id }) { feedPost ->
//            val dismissThresholds = with(receiver = LocalDensity.current) {
//                LocalConfiguration.current.screenWidthDp.dp.toPx() * 0.5F
//            }
//            val dismissState = SwipeToDismissBoxState(
//                initialValue = SwipeToDismissBoxValue.Settled,
//                density = LocalDensity.current,
//                confirmValueChange = { value ->
//                    val isDismissed = value in setOf(
//                        SwipeToDismissBoxValue.EndToStart,
//                    )
//                    if (isDismissed) {
//                        viewModel.remove(feedPost)
//                        true
//                    }
//                    false
//                },
//                positionalThreshold = { dismissThresholds }
//            )
//            SwipeToDismissBox(
//                modifier = Modifier.animateItem(),
//                state = dismissState,
//                backgroundContent = {}) {
//                PostCard(
//                    feedPost = feedPost,
//                    onLikeClickListener = { statisticItem ->
//                        viewModel.updateCount(feedPost, statisticItem)
//                    },
//                    onShareClickListener = { statisticItem ->
//                        viewModel.updateCount(feedPost, statisticItem)
//                    },
//                    onViewsClickListener = { statisticItem ->
//                        viewModel.updateCount(feedPost, statisticItem)
//                    },
//                    onCommentClickListener = { statisticItem ->
//                        viewModel.updateCount(feedPost, statisticItem)
//                    },
//                )
//            }
//
//        }
//    }
}