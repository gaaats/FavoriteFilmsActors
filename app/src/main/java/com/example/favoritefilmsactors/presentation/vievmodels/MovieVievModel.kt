package com.example.favoritefilmsactors.presentation.vievmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.GetMovImagesUseCase
import com.example.favoritefilmsactors.domain.usecase.GetMoviesUseCase
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieVievModel @Inject constructor(
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase,
    private val getImages: GetMovImagesUseCase,
) : ViewModel() {

    private var _listImages = MutableLiveData<List<ImagesItem>>()
    val listImages: LiveData<List<ImagesItem>>
        get() = _listImages


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


    suspend fun loadImagesList(imageId:Int){
        withContext(Dispatchers.Main){
            _listImages.value = getImages.invoke(imageId)
        }
    }
    fun checkInside(){
        for (element in listImages.value!!){
            Log.d(Constance.TAG, "inside livedata: ${element.filePath}")
        }
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


