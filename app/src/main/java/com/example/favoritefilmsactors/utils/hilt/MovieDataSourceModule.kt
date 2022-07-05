package com.example.favoritefilmsactors.utils.hilt

import com.example.favoritefilmsactors.data.repo.datasource.impl.movie.MovieCachDataSourceImpl
import com.example.favoritefilmsactors.data.repo.datasource.impl.movie.MovieLocalDataSourceImpl
import com.example.favoritefilmsactors.data.repo.datasource.impl.movie.MovieRemoteDataSourceImpl
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieLocalDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface MovieDataSourceModule {

    @Binds
    @Singleton
    fun bindsLocalDataSource(impl: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds
    @Singleton
    fun bindsCacheDataSource(impl: MovieCachDataSourceImpl): MovieCacheDataSource

    @Binds
    @Singleton
    fun bindsRemoteDataSource(impl: MovieRemoteDataSourceImpl): MovieRemoteDataSource




}