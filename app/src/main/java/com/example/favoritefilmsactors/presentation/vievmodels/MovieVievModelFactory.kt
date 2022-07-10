package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.GetMovImagesUseCase
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import com.example.favoritefilmsactors.domain.usecase.GetSearchedMoviesByNameUseCase
import javax.inject.Inject

class MovieVievModelFactory @Inject constructor(
    private val application: Application,
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase,
    private val getMovImagesUseCase: GetMovImagesUseCase,
    private val getSearchedMoviesByName: GetSearchedMoviesByNameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieVievModel(
            application,
            getMovies,
            updateMovie,
            getMovImagesUseCase,
            getSearchedMoviesByName
        ) as T
    }
}