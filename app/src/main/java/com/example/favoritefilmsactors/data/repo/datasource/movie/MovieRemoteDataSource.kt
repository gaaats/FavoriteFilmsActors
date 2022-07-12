package com.example.favoritefilmsactors.data.repo.datasource.movie

import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import com.example.favoritefilmsactors.utils.SimpleResponse
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun downloadMoviesFromNet(pageIndex:Int): SimpleResponse<MovieList>
    suspend fun downloadImagesFromNet(movieId: Int): SimpleResponse<ImagesList>
    suspend fun getSearchedMoviesByName(querySearch: String, pageIndex: Int): SimpleResponse<MovieList>
}