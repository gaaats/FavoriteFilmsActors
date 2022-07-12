package com.example.favoritefilmsactors.domain

import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.ResourceVrap

interface MovieRepository {

    suspend fun getMovies(pageIndex:Int): ResourceVrap<List<MovieSimple>>
    suspend fun getSearchedMoviesByNameUseCase(query:String): List<MovieSimple>?
    suspend fun getListOfImages(imageId:Int):List<ImagesItem>
    suspend fun saveSingleMovieToWishlist(movie: MovieItemEntityDB)
    suspend fun getMoviesFromDataBase(): List<MovieItemEntityDB>
    suspend fun deleteSingleMovieFromWishlist(movieId: Int)
}