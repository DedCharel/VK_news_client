package ru.nvgsoft.vknewsclient.data.mapper

import android.util.Log
import ru.nvgsoft.vknewsclient.data.model.NewsFeedResponseDto
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.domain.StatisticType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPost(responseDto: NewsFeedResponseDto): List<FeedPost>{
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            if (post.id == null || post.id == 0L) continue
            Log.d("Mapper", "${post.id}")
            val group = groups.find { it.id == post.communityId.absoluteValue } // absoluteValue берем для того чтобы убрать минус
            val feedPost = FeedPost(
                id = post.id,
                communityId = post.communityId,
                communityName = group?.name ?: "",
                publicationData = mapTimestampToData(post.date * 1000),
                communityImageUrl = group?.imageUrl ?: "",
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                ),
                isLiked = post.likes.userLikes > 0
            )
            result.add(feedPost)
        }
        return result
    }

    private fun mapTimestampToData(timestamp: Long): String {
        val data = Date(timestamp)
        return SimpleDateFormat("d MMMM yyyy, hh:mm", Locale.getDefault()).format(data)
    }
}