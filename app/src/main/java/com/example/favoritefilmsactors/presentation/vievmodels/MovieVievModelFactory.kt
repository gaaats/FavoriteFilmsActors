package com.example.favoritefilmsactors.presentation.vievmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase

class MovieVievModelFactory(
//    private val getMovies: GetMoviesUseCase,
//    private val updateMovie: GetMoviesUseCase
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieVievModel() as T
    }
}