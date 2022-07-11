package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.*
import com.example.favoritefilmsactors.presentation.paging.MoviesListPagingSource
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieVievModel @Inject constructor(
    private val application: Application,
    private val getMovies: GetMoviesUseCase,
    private val updateMovie: GetMoviesUseCase,
    private val getImages: GetMovImagesUseCase,
    private val saveSingleMovieToWishlist: SaveSingleMovieToWishlist,
    private val getMoviesWishlist: GetMoviesWishlist,
    private val deleteSingleMovieFromWishlist: DeleteSingleMovieFromWishlist,
    private val getSearchedMoviesByName: GetSearchedMoviesByNameUseCase
) : ViewModel() {

    private val _statusMessage = MutableLiveData<EventVraper<String>>()
    val statusMessage: LiveData<EventVraper<String>>
        get() = _statusMessage

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

    private var _wishlistIsEmptyOrNull = MutableLiveData<Boolean>()
    val wishlistIsEmptyOrNull: LiveData<Boolean>
        get() = _wishlistIsEmptyOrNull

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        }
        chechWishlistNullOrEmpty()
    }
    suspend fun loadForTakeChanges(){
        CoroutineScope(Dispatchers.IO).launch {
            _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        }
    }

    private lateinit var moviesFlow: StateFlow<List<MovieSimple>>

    suspend fun deleteSingleMovieFromWishlist(movieId: Int) {
        deleteSingleMovieFromWishlist.execute(movieId)
//        _statusMessage.postValue(EventVraper("movie DELETED"))
        chechWishlistNullOrEmpty()
    }

    suspend fun loadFavoriteMovies() {
        itIsLoadingNov()
        delay(1000)
        _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        chechWishlistNullOrEmpty()
        loadingFinished()

    }

    private fun chechWishlistNullOrEmpty() {
        if (listFavoriteMovies.value.isNullOrEmpty()) _wishlistIsEmptyOrNull.postValue(true)
        else _wishlistIsEmptyOrNull.postValue(false)
    }

    //TESTING PAGING

    val getFlovMovies = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        PagingConfig(
            pageSize = Constance.PAGE_SIZE,
            prefetchDistance = Constance.PREFETCH_DISTANCE,
            enablePlaceholders = false
        )
    ) {
        viewModelScope.launch {
            itIsLoadingNov()
            delay(1000)
            loadingFinished()
        }
        MoviesListPagingSource(getMovies)
    }.flow
        .cachedIn(viewModelScope)

    /*
    // FOR NORMAL APP
    val getFlovMovies = flow {
        itIsLoadingNov()
        delay(1000)
        val items = getMovies.invoke(2) ?: throw RuntimeException("getFlovMovies is null")
        emit(items)
        loadingFinished()
    }

     */

    val updateFlovMovies = flow {
        val items = updateMovie.invoke() ?: throw RuntimeException("updateFlovMovies is null")
        emit(items)
    }

    suspend fun addSingleMovieToWishlist(movie: MovieSimple) {
//        val oldList = listOf(listFavoriteMovies.value)
        saveSingleMovieToWishlist.execute(movie)
//        if (oldList != listFavoriteMovies.value){
//            _statusMessage.postValue(EventVraper("movie successfully add to Wishlist"))
//        }
//        Log.d(Constance.TAG, "add to favorite")
    }

    suspend fun fakeDeleteOfItem(movie: MovieSimple){
        deleteSingleMovieFromWishlist.execute(movie.id)
//        _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        saveSingleMovieToWishlist.execute(movie)
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

    fun makeTestLog() {
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


