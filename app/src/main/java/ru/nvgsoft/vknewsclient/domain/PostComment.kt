package ru.nvgsoft.vknewsclient.domain

import ru.nvgsoft.vknewsclient.R

data class PostComment(
    val id: Long,
    val authorName: String ,
    val authorAvatarUrl: String ,
    val commentText: String ,
    val publicationDate: String
)
