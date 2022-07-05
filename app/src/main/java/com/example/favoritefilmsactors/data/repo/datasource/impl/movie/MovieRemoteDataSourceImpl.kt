package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import retrofit2.Response

class MovieRemoteDataSourceImpl (
    private val apiService: TMDBService,
    private val apiKey: String
    ): MovieRemoteDataSource {
    override suspend fun downloadMoviesFromNet(): Response<MovieList> {
        return apiService.getPopularMovies(apiKey)
    }
}