package ru.nvgsoft.vknewsclient.data.mapper

import ru.nvgsoft.vknewsclient.data.model.NewsFeedResponseDto
import ru.nvgsoft.vknewsclient.domain.FeedPost
import ru.nvgsoft.vknewsclient.domain.StatisticItem
import ru.nvgsoft.vknewsclient.domain.StatisticType
import kotlin.math.absoluteValue

class NewsFeedMapper {

    fun mapResponseToPost(responseDto: NewsFeedResponseDto): List<FeedPost>{
        val result = mutableListOf<FeedPost>()

        val posts = responseDto.newsFeedContent.posts
        val groups = responseDto.newsFeedContent.groups

        for (post in posts) {
            val group = groups.find { it.id == post.communityId.absoluteValue } ?: break// absoluteValue берем для того чтобы убрать минус
            val feedPost = FeedPost(
                id = post.id,
                communityName = group.name,
                publicationData = post.date.toString(),
                communityImageUrl = group.imageUrl,
                contentText = post.text,
                contentImageUrl = post.attachments?.firstOrNull()?.photo?.photoUrls?.lastOrNull()?.url,
                statistics = listOf(
                    StatisticItem(type = StatisticType.LIKES, post.likes.count),
                    StatisticItem(type = StatisticType.VIEWS, post.views.count),
                    StatisticItem(type = StatisticType.SHARES, post.reposts.count),
                    StatisticItem(type = StatisticType.COMMENTS, post.comments.count)
                )
            )
            result.add(feedPost)
        }
        return result
    }
}