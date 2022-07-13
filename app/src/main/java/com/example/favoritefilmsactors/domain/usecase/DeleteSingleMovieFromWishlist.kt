package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.domain.repo.MovieRepository
import javax.inject.Inject

class DeleteSingleMovieFromWishlist @Inject constructor(private val repository: MovieRepository) {
    suspend fun execute(movieId: Int) {
        repository.deleteSingleMovieFromWishlist(movieId)
    }
}