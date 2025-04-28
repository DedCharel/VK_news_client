package ru.nvgsoft.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.nvgsoft.vknewsclient.data.model.NewsFeedResponseDto

interface ApiService {
    @GET("newsfeed.getRecommended?v=5.199")
    suspend fun loadPosts(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}