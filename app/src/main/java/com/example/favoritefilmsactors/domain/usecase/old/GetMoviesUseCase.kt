package com.example.favoritefilmsactors.domain.usecase.old

import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.ResourceVrap
import javax.inject.Inject

//class GetMoviesUseCase @Inject constructor (private val repository: MovieRepository) {
//
//    suspend operator fun invoke(pageIndex: Int = FIRST_PAGE_FOR_LOADING): ResourceVrap<List<MovieSimple>> {
//        return repository.getMovies(pageIndex = pageIndex)
//    }
//    companion object{
//        private const val FIRST_PAGE_FOR_LOADING = 1
//    }
//}