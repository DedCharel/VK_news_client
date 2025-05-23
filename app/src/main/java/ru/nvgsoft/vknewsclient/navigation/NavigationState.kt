package ru.nvgsoft.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost

class NavigationState(
    val navHostController: NavHostController
) {

    fun navigateTo(rout: String){
        navHostController.navigate(rout) {
            popUpTo(navHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToComments(feedPost: FeedPost){
        navHostController.navigate(Screen.Comments.getRouteWithArgs(feedPost)) //comments/15
    }
}

@Composable
fun rememberNavigationState(
    navHostController: NavHostController = rememberNavController()
): NavigationState{
    return remember {
        NavigationState(navHostController)
    }
}