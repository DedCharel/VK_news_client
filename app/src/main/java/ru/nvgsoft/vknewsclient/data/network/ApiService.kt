package ru.nvgsoft.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.nvgsoft.vknewsclient.data.model.LikesCountResponseDto
import ru.nvgsoft.vknewsclient.data.model.NewsFeedResponseDto

interface ApiService {

    @GET("newsfeed.get?v=5.199&type=post")
    suspend fun loadPosts(
        @Query("access_token") token: String
    ): NewsFeedResponseDto

    @GET("newsfeed.get?v=5.199&type=post")
    suspend fun loadPosts(
        @Query("access_token") token: String,
        @Query("start_from") startFrom: String
    ): NewsFeedResponseDto


    @GET("likes.add?v=5.199&type=post")
    suspend fun addLike(
        @Query("access_token")token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("likes.delete?v=5.199&type=post")
    suspend fun deleteLike(
        @Query("access_token")token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    ): LikesCountResponseDto

    @GET("newsfeed.ignoreItem?v=5.199&type=wall")
    suspend fun ignorePost(
        @Query("access_token")token: String,
        @Query("owner_id") ownerId: Long,
        @Query("item_id") itemId: Long
    )

}