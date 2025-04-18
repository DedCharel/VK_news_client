package ru.nvgsoft.vknewsclient.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import ru.nvgsoft.vknewsclient.domain.FeedPost

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    favouriteScreenContent: @Composable () -> Unit,
    profileScreenContent: @Composable () -> Unit,
    newsFeedScreenContent: @Composable () -> Unit,
    commentsScreenContent: @Composable (FeedPost) -> Unit

){
    NavHost(
        navController = navHostController ,
        startDestination =Screen.Home.route
    ) {
        homeScreenNavGraph(
            newsFeedScreenContent =newsFeedScreenContent,
            commentsScreenContent = commentsScreenContent
        )

        composable(Screen.Favourite.route){
            favouriteScreenContent()
        }
        composable(Screen.Profile.route){
            profileScreenContent()
        }
    }
}