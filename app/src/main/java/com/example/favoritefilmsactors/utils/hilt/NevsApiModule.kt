package com.example.favoritefilmsactors.utils.hilt

import com.example.favoritefilmsactors.BuildConfig
import com.example.favoritefilmsactors.data.remote.api.NewsAPIService
import com.example.favoritefilmsactors.data.remote.api.TMDBService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NevsApiModule {

    @Provides
    @Singleton
    fun providesNevsAPIService(): NewsAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_NEVS_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(NewsAPIService::class.java)
    }
}