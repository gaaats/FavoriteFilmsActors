package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.*
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
    private val application: Application,
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase,
    private val getImages: GetMovImagesUseCase,
    private val saveSingleMovieToWishlist: SaveSingleMovieToWishlist,
    private val getMoviesWishlist: GetMoviesWishlist,
    private val getSearchedMoviesByName: GetSearchedMoviesByNameUseCase
) : ViewModel() {

    private var _listImages = MutableLiveData<List<ImagesItem>>()
    val listImages: LiveData<List<ImagesItem>>
        get() = _listImages

    private var _listMovies = MutableLiveData<List<MovieSimple>>()
    val listMovies: LiveData<List<MovieSimple>>
        get() = _listMovies

    private var _listFavoriteMovies = MutableLiveData<List<MovieSimple>>()
    val listFavoriteMovies: LiveData<List<MovieSimple>>
        get() = _listFavoriteMovies


    private var _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading

    private lateinit var moviesFlow: StateFlow<List<MovieSimple>>

    suspend fun loadFavoriteMovies() {
        _listFavoriteMovies.postValue(getMoviesWishlist.execute())
    }

    // FOR NORMAL APP
    val getFlovMovies = flow {
        itIsLoadingNov()
        delay(1000)
        val items = getMovies.invoke() ?: throw RuntimeException("getFlovMovies is null")
        emit(items)
        loadingFinished()
    }

    val updateFlovMovies = flow {
        val items = updateMovie.invoke() ?: throw RuntimeException("updateFlovMovies is null")
        emit(items)
    }

    suspend fun addSingleMovieToWishlist(movie: MovieSimple) {
        saveSingleMovieToWishlist.execute(movie)
        Log.d(Constance.TAG, "add to favorite")
    }

    suspend fun searchMovieByName(searcherQuery: String) {
        itIsLoadingNov()
        viewModelScope.launch {
            try {
                if (isNetAvailable(application)) {
                    withContext(Dispatchers.Main) {
                        _listMovies.value = getSearchedMoviesByName.execute(searcherQuery)
                        loadingFinished()
                    }
                } else {
                    throw RuntimeException("Internet problem")
                }
            } catch (e: Exception) {
                throw RuntimeException("catch MovieVievModel - searchMovieByName")
            }
        }

    }

    private suspend fun itIsLoadingNov() {
        withContext(Dispatchers.Main) {
            _loading.value = true
        }
    }

    private suspend fun loadingFinished() {
        withContext(Dispatchers.Main) {
            _loading.value = false
        }
    }

    suspend fun loadImagesList(imageId: Int) {
        withContext(Dispatchers.Main) {
            _listImages.value = getImages.invoke(imageId)
        }
    }

    fun checkInside() {
        for (element in listImages.value!!) {
            Log.d(Constance.TAG, "inside livedata: ${element.filePath}")
        }
    }

    fun isNetAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }

            }
        } else {
            val activeNetvorkInfo = connectivityManager.activeNetworkInfo
            if (activeNetvorkInfo != null && activeNetvorkInfo.isConnected) return true
        }
        return false
    }
    fun makeTestLog(){
        Log.d(Constance.TAG, "vievmodel is vork")
    }
/*
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
 */
}
/*

//
//    private fun getMovies() {
//        viewModelScope.launch {
//            moviesFlow = flow {
//                val items = getMovies.invoke() ?: throw RuntimeException("moviesFlov is null")
//                emit(items)
//            } as MutableStateFlow<List<MovieSimple>>
//        }
//    }

 */


