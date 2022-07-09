package com.example.favoritefilmsactors.presentation.vievmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.GetMovImagesUseCase
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import javax.inject.Inject

class MovieVievModelFactory @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase,
    private val getMovImagesUseCase: GetMovImagesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieVievModel(getMovies, updateMovie, getMovImagesUseCase) as T
    }
}