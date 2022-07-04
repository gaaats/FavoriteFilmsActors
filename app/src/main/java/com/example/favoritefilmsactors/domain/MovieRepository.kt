package com.example.favoritefilmsactors.domain

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.entity.MovieSimple

interface MovieRepository {

    suspend fun getMovies(): List<MovieSimple>?
    suspend fun updateMovies():List<MovieItemNetEntity>?
}