package ru.nvgsoft.vknewsclient.domain

import ru.nvgsoft.vknewsclient.R

data class PostComment(
    val id: Int,
    val authorName: String = "Author",
    val authorAvatarId: Int = R.drawable.comment_author_avatar,
    val commentText: String = "Long text comment",
    val publicationDate: String = "14:00"
)
