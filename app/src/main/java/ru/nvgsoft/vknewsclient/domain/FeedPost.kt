package ru.nvgsoft.vknewsclient.domain

import ru.nvgsoft.vknewsclient.R

data class FeedPost(
    val id: Int = 0,
    val communityName: String = "/dev/null",
    val publicationData: String = "14:00",
    val avatarResId: Int =  R.drawable.post_comunity_thumbnail,
    val contentText: String = "кабаныч, когда узнал, что если сотрудникам не платить они начнут умирать от голода",
    val contentImageResId: Int = R.drawable.post_content_image,
    val statistics: List<StatisticItem> = listOf(
        StatisticItem(StatisticType.VIEWS, 966),
        StatisticItem(StatisticType.SHARES, 7),
        StatisticItem(StatisticType.COMMENTS, 8),
        StatisticItem(StatisticType.LIKES, 27)
    )
) {
}