package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple

class GetMoviesUseCase (private val repository: MovieRepository) {

    suspend operator fun invoke(): List<MovieSimple>? {
        return repository.getMovies()
    }
}