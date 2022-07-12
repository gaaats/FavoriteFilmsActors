package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.liveData
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
    private val getMovies: SearchMovieParentUseCase.GetMoviesUseCase,
    private val getImages: GetMovImagesUseCase,
    private val saveSingleMovieToWishlist: SaveSingleMovieToWishlist,
    private val getMoviesWishlist: GetMoviesWishlist,
    private val deleteSingleMovieFromWishlist: DeleteSingleMovieFromWishlist,
    private val getSearchedMoviesByName: SearchMovieParentUseCase.GetSearchedMoviesByNameUseCase
) : ViewModel() {

    var tempQuery = "empty"

    val moviesListPagingSourcePopularSearch = MoviesListPagingSource(getMovies, "empty")
    val moviesListPagingSourceSearchByName =
        MoviesListPagingSource(getSearchedMoviesByName, tempQuery)

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

        moviesListPagingSourceSearchByName.tempQuery = tempQuery
    }

    suspend fun loadForTakeChanges() {
        CoroutineScope(Dispatchers.IO).launch {
            _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        }
    }

    private lateinit var moviesFlow: StateFlow<List<MovieSimple>>

    suspend fun deleteSingleMovieFromWishlist(movieId: Int) {
        deleteSingleMovieFromWishlist.execute(movieId)
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
        moviesListPagingSourcePopularSearch
    }.flow
        .cachedIn(viewModelScope)

    private var currentQuery = MutableLiveData("empty")

    fun changeCurrentQuery(nextQuery: String){
        currentQuery.value = nextQuery
    }

    val testMoviesSearchByNamePaging = currentQuery.switchMap {
        testPagingSearchByName(it)
    }

    fun testPagingSearchByName(query: String) =
        Pager(
            PagingConfig(
                pageSize = Constance.PAGE_SIZE,
                prefetchDistance = Constance.PREFETCH_DISTANCE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                MoviesListPagingSource(getSearchedMoviesByName, query)
            }
        ).liveData


    val searchMovieByNamePaging = Pager(
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
        MoviesListPagingSource(getSearchedMoviesByName, tempQuery)
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

    suspend fun addSingleMovieToWishlist(movie: MovieSimple) {
        saveSingleMovieToWishlist.execute(movie)
    }

    suspend fun fakeDeleteOfItem(movie: MovieSimple) {
        deleteSingleMovieFromWishlist.execute(movie.id)
        saveSingleMovieToWishlist.execute(movie)
    }

    fun searchMovieByName(searcherQuery: String) {
        tempQuery = searcherQuery
        moviesListPagingSourceSearchByName.tempQuery = tempQuery
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
            _listImages.value = getImages(imageId).data
        }
    }
/*
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

 */

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


