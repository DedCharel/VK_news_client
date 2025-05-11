package ru.nvgsoft.vknewsclient.navigation

import android.net.Uri
import com.google.gson.Gson
import ru.nvgsoft.vknewsclient.domain.entity.FeedPost

sealed class Screen (
    val route: String
){

    object NewsFeed: Screen(ROUTE_NEWS_FEED)
    object Favourite: Screen(ROUTE_FAVOURITE)
    object Profile: Screen(ROUTE_PROFILE)
    object Comments: Screen(ROUTE_COMMENTS) {

        private const val ROUTE_FOR_ARGS = "comments"
        fun getRouteWithArgs(feedPost: FeedPost): String{
            val feedPostGson = Gson().toJson(feedPost)
            return "$ROUTE_FOR_ARGS/${feedPostGson.encode()}"
        }
    }
    object Home: Screen(ROUTE_HOME)


    companion object{

       const val KEY_FEED_POST = "feed_post"

        const val ROUTE_HOME = "home"
        const val ROUTE_COMMENTS = "comments/{$KEY_FEED_POST}"
        const val ROUTE_NEWS_FEED = "news_feed"
        const val ROUTE_FAVOURITE = "favourite"
        const val ROUTE_PROFILE = "profile"
    }
}

//Экранируем спецсимволы
fun String.encode(): String {
    return Uri.encode(this)
}