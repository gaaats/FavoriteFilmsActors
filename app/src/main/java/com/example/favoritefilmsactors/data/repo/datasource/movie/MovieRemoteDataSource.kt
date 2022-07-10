package com.example.favoritefilmsactors.data.repo.datasource.movie

import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun downloadMoviesFromNet(): Response<MovieList>
    suspend fun downloadImagesFromNet(movieId: Int): Response<ImagesList>
    suspend fun getSearchedMoviesByName(querySearch: String): Response<MovieList>
}