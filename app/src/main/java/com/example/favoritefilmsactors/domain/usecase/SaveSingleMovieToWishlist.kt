package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.repo.MovieRepository
import javax.inject.Inject

class SaveSingleMovieToWishlist @Inject constructor(private val repository: MovieRepository) {
    suspend fun execute(movie: MovieSimple){
        repository.saveSingleMovieToWishlist(movie.convertToDataBaseEntity())
    }
}