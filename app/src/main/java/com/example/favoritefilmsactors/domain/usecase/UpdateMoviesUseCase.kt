package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.MovieRepository

class UpdateMoviesUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): List<MovieItemNetEntity>? {
        return repository.updateMovies()
    }
}