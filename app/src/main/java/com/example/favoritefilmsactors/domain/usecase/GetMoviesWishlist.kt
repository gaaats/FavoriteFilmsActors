package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import javax.inject.Inject

class GetMoviesWishlist @Inject constructor(private val repository: MovieRepository) {
    suspend fun execute(): List<MovieSimple>{
        val result = repository.getMoviesFromDataBase().map {
            it.convertToSimpleEntity()
        }
        return result
    }
}