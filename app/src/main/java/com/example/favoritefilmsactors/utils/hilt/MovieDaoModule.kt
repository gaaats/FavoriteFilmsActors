package com.example.favoritefilmsactors.utils.hilt

import android.app.Application
import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.room.TMDbDataBase
import com.example.favoritefilmsactors.data.room.dao.MoviesDao
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MovieDaoModule {

    @Provides
    @Singleton
    fun providesMovieDao(application: Application): MoviesDao {
        return TMDbDataBase.getDataBase(application).getMoviesDao()
    }

    @Provides
    @Singleton
    fun providesTMDBService(): TMDBService {
        val retrofit = Retrofit.Builder()
            .baseUrl(TMDBService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(TMDBService::class.java)
    }
}