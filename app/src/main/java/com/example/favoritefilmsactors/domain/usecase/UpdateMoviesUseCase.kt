package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.MovieRepository
import javax.inject.Inject

class UpdateMoviesUseCase @Inject constructor (private val repository: MovieRepository) {

    suspend operator fun invoke(): List<MovieItemNetEntity>? {
        return repository.updateMovies()
    }
}