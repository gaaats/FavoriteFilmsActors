package com.example.favoritefilmsactors.domain

import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShowItemNetEntity

interface TvShovsRepository {

    suspend fun getTvShovs():List<TvShowItemNetEntity>?
    suspend fun updateTvShovs():List<TvShowItemNetEntity>?
}