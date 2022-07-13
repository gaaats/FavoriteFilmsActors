package com.example.favoritefilmsactors.utils.hilt

import com.example.favoritefilmsactors.data.repo.datasource.nevs.NevsRemoteDataSource
import com.example.favoritefilmsactors.data.repo.datasource.nevs.NevsRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface NevsDataSourceModule {

    @Binds
    @Singleton
    fun bindsRemoteDataSource(impl: NevsRemoteDataSourceImpl): NevsRemoteDataSource
}