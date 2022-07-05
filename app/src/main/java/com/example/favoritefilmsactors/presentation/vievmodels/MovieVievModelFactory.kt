package com.example.favoritefilmsactors.presentation.vievmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import javax.inject.Inject

class MovieVievModelFactory @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieVievModel(getMovies, updateMovie) as T
    }
}