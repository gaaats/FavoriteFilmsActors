package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: TMDBService,
    // TRY TO ADD HERE  private val apiKey: String

    ): MovieRemoteDataSource {
    override suspend fun downloadMoviesFromNet(): Response<MovieList> {
        return apiService.getPopularMovies()
    }

    override suspend fun downloadImagesFromNet(movieId:Int): Response<ImagesList> {
        return apiService.getMoviesImages(movieId)
    }
}