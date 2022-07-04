package com.example.favoritefilmsactors.data.repo.datasource.movie

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.entity.MovieSimple

interface MovieCacheDataSource {

    suspend fun getMoviesFormCache(): List<MovieSimple>
    suspend fun saveMoviesToCache(movies:List<MovieSimple>)
}