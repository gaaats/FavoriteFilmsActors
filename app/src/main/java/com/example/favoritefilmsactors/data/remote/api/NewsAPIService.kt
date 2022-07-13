package com.example.favoritefilmsactors.data.remote.api

import com.example.favoritefilmsactors.BuildConfig
import com.example.favoritefilmsactors.data.remote.models.nevs.NevsApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPIService {

    companion object {
        private const val COUNT_OF_PAGES_FOR_LOADING = 1
    }

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY_NEVS,
        @Query("page") page: Int = 1,
        @Query("q") queryAbout: String = "ua"
    ): Response<NevsApiResponse>
}