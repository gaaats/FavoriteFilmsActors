package com.example.favoritefilmsactors.presentation.vievmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MovieVievModel(

) : ViewModel() {

//    private val getMovies = GetMoviesUseCase()
//    private val updateMovie = GetMoviesUseCase()

    private lateinit var moviesFlow: StateFlow<List<MovieSimple>>

    // FOR NORMAL APP
//    val getFlovMovies = flow {
//        val items = getMovies.invoke() ?: throw RuntimeException("getFlovMovies is null")
//        emit(items)
//    }

//    val updateFlovMovies = flow {
//        val items = updateMovie.invoke() ?: throw RuntimeException("updateFlovMovies is null")
//        emit(items)
//    }

    //TEST
    val getFlovMovies = flow {
        val items = generateList() ?: throw RuntimeException("getFlovMovies is null")
        emit(items)
    }

    fun generateList(): List<MovieSimple>? {
        val one = MovieSimple(1, "harry potter", "poster", "data", "potter title")
        val tvo = MovieSimple(2, "star vars", "poster", "data", "star vars title")
        val three = MovieSimple(3, "vlastelin kolets", "poster", "data", "vlastelin kolets title")
        val list = listOf(one,tvo,three)
        return list
    }

}


//
//    private fun getMovies() {
//        viewModelScope.launch {
//            moviesFlow = flow {
//                val items = getMovies.invoke() ?: throw RuntimeException("moviesFlov is null")
//                emit(items)
//            } as MutableStateFlow<List<MovieSimple>>
//        }
//    }


