package com.example.favoritefilmsactors.utils.hilt

import com.example.favoritefilmsactors.data.repo.MoviesRepositoryImpl
import com.example.favoritefilmsactors.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {

    @Binds
    @Singleton
    fun bindsRepository(impl: MoviesRepositoryImpl): MovieRepository

}