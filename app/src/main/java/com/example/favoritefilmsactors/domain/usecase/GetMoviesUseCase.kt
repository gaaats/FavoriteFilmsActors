package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor (private val repository: MovieRepository) {

    suspend operator fun invoke(): List<MovieSimple>? {
        return repository.getMovies()
    }
}