package com.example.favoritefilmsactors.presentation.vievmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieVievModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase
) : ViewModel() {

    private var _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private lateinit var moviesFlow: StateFlow<List<MovieSimple>>

    // FOR NORMAL APP
    val getFlovMovies = flow {
        _loading.value=true
        delay(1000)
        val items = getMovies.invoke() ?: throw RuntimeException("getFlovMovies is null")
        emit(items)
        _loading.value = false
    }

    val updateFlovMovies = flow {
        val items = updateMovie.invoke() ?: throw RuntimeException("updateFlovMovies is null")
        emit(items)
    }

    //TEST
//    val getFlovMovies = flow {
//        val items = generateList() ?: throw RuntimeException("getFlovMovies is null")
//        emit(items)
//    }
//
//    fun generateList(): List<MovieSimple>? {
//        val one = MovieSimple(1, "harry potter", "poster", "data", "potter title")
//        val tvo = MovieSimple(2, "star vars", "poster", "data", "star vars title")
//        val three = MovieSimple(3, "vlastelin kolets", "poster", "data", "vlastelin kolets title")
//        val list = listOf(one, tvo, three)
//        return list
//    }

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


