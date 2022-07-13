package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.repo.MovieRepository
import com.example.favoritefilmsactors.utils.ResourceVrap
import javax.inject.Inject

sealed class SearchMovieParentUseCase {

    abstract suspend fun execute(query: String, pageIndex: Int): ResourceVrap<List<MovieSimple>>

    class GetSearchedMoviesByNameUseCase @Inject constructor(private val repository: MovieRepository) :
        SearchMovieParentUseCase() {

        override suspend fun execute(
            query: String,
            pageIndex: Int
        ): ResourceVrap<List<MovieSimple>> {
            return repository.getSearchedMoviesByNameUseCase(query, pageIndex)
        }

    }

    class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) :
        SearchMovieParentUseCase() {

        override suspend fun execute(
            query: String,
            pageIndex: Int
        ): ResourceVrap<List<MovieSimple>> {
            return repository.getMovies(pageIndex = pageIndex)
        }
    }

    companion object {
        private const val FIRST_PAGE_FOR_LOADING = 1
    }


}