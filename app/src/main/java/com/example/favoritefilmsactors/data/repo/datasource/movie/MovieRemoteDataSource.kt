package com.example.favoritefilmsactors.data.repo.datasource.movie

import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun downloadMoviesFromNet():Response<MovieList>
}