package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.remote.api.TMDBService
import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import com.example.favoritefilmsactors.utils.SimpleResponse
import retrofit2.Response
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiService: TMDBService,
    // TRY TO ADD HERE  private val apiKey: String

) : MovieRemoteDataSource {
    override suspend fun downloadMoviesFromNet(pageIndex: Int): SimpleResponse<MovieList> {
        return safeApiCall { apiService.getPopularMovies(page = pageIndex) }
    }

    override suspend fun downloadImagesFromNet(movieId: Int): SimpleResponse<ImagesList> {
        return safeApiCall { apiService.getMoviesImages(movieId) }
    }

    override suspend fun getSearchedMoviesByName(querySearch: String, pageIndex: Int): SimpleResponse<MovieList> {
        return safeApiCall { apiService.getSearchedMoviesByName(query = querySearch, page = pageIndex)}
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}