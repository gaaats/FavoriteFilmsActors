package com.example.favoritefilmsactors.domain.usecase

import androidx.room.Index
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.presentation.vievmodels.ResourceVrap
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor (private val repository: MovieRepository) {

    suspend operator fun invoke(pageIndex: Int = FIRST_PAGE_FOR_LOADING): ResourceVrap<List<MovieSimple>> {
        return repository.getMovies(pageIndex = pageIndex)
    }
    companion object{
        private const val FIRST_PAGE_FOR_LOADING = 1
    }
}