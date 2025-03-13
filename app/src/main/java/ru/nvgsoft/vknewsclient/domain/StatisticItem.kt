package ru.nvgsoft.vknewsclient.domain

data class StatisticItem(
    val type: StatisticType,
    val count: Int
) {
}

enum class StatisticType {
    VIEWS, COMMENTS, SHARES, LIKES
}