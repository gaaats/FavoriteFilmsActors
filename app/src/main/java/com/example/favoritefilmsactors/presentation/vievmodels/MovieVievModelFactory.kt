package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.favoritefilmsactors.domain.usecase.*
import javax.inject.Inject

class MovieVievModelFactory @Inject constructor(
    private val application: Application,
    private val getMovies: SearchMovieParentUseCase.GetMoviesUseCase,
    private val getMovImagesUseCase: GetMovImagesUseCase,
    private val saveSingleMovieToWishlist: SaveSingleMovieToWishlist,
    private val getMoviesWishlist: GetMoviesWishlist,
    private val deleteSingleMovieFromWishlist: DeleteSingleMovieFromWishlist,
    private val getSearchedMoviesByName: SearchMovieParentUseCase.GetSearchedMoviesByNameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieVievModel(
            application,
            getMovies,
            getMovImagesUseCase,
            saveSingleMovieToWishlist,
            getMoviesWishlist,
            deleteSingleMovieFromWishlist,
            getSearchedMoviesByName
        ) as T
    }
}