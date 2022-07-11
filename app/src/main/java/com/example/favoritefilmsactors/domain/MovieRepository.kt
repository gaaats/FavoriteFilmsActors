package com.example.favoritefilmsactors.domain

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple

interface MovieRepository {

    suspend fun getMovies(): List<MovieSimple>?
    suspend fun getSearchedMoviesByNameUseCase(query:String): List<MovieSimple>?
    suspend fun updateMovies():List<MovieItemNetEntity>?
    suspend fun getListOfImages(imageId:Int):List<ImagesItem>
    suspend fun saveSingleMovieToWishlist(movie: MovieItemEntityDB)
    suspend fun getMoviesFromDataBase(): List<MovieItemEntityDB>
}