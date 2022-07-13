package com.example.favoritefilmsactors.presentation.vievmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.paging.*
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.domain.usecase.*
import com.example.favoritefilmsactors.presentation.paging.MoviesListPagingSource
import com.example.favoritefilmsactors.utils.CurrentState
import com.example.favoritefilmsactors.utils.CustomMovieException
import com.example.favoritefilmsactors.utils.ResourceVrap
import com.example.favoritefilmsactors.utils.SimpleResponse
import com.example.favoritefilmsactors.utils.constance.Constance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MovieVievModel @Inject constructor(
    private val application: Application,
    private val getMovies: SearchMovieParentUseCase.GetMoviesUseCase,
    private val saveSingleMovieToWishlist: SaveSingleMovieToWishlist,
    private val getMoviesWishlist: GetMoviesWishlist,
    private val deleteSingleMovieFromWishlist: DeleteSingleMovieFromWishlist,
    private val getSearchedMoviesByName: SearchMovieParentUseCase.GetSearchedMoviesByNameUseCase
) : ViewModel() {

    var tempQuery = "empty"

    private val currentQueryFromInputFlov = MutableStateFlow("empty")

    val moviesListPagingSourcePopularSearch = MoviesListPagingSource(getMovies, tempQuery)

    private val _statusMessage = MutableLiveData<EventVraper<String>>()
    val statusMessage: LiveData<EventVraper<String>>
        get() = _statusMessage

    private var _listImages = MutableLiveData<List<ImagesItem>>()
    val listImages: LiveData<List<ImagesItem>>
        get() = _listImages

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

    // live data - paging3
/*
    private var currentQuery = MutableLiveData("empty")

    fun changeCurrentQueryFromInput(inputQuery: String) {
        currentQuery.value = inputQuery
    }

    val testMoviesSearchByNamePaging = currentQuery.switchMap {
        testPagingSearchByNameUsingRetrofit(it)
    }

    private fun testPagingSearchByNameUsingRetrofit(query: String) =
        Pager(
            PagingConfig(
                pageSize = Constance.PAGE_SIZE,
                prefetchDistance = Constance.PREFETCH_DISTANCE,
                enablePlaceholders = false
            ), pagingSourceFactory = {
                MoviesListPagingSource(getSearchedMoviesByName, query)
            }
        ).liveData
 */

    suspend fun deleteSingleMovieFromWishlist(movieId: Int) {
        deleteSingleMovieFromWishlist.execute(movieId)
        chechWishlistNullOrEmpty()
        delay(400)
        _listFavoriteMovies.postValue(getMoviesWishlist.execute())
    }

    suspend fun loadFavoriteMovies() {
        itIsLoadingNov()
        delay(1000)
        try {
            _listFavoriteMovies.postValue(getMoviesWishlist.execute())
        } catch (e: Exception) {
            _statusMessage.value = EventVraper(e.message!!)
        }
        chechWishlistNullOrEmpty()
        loadingFinished()
    }

    private fun chechWishlistNullOrEmpty() {
        if (listFavoriteMovies.value.isNullOrEmpty()) _wishlistIsEmptyOrNull.postValue(true)
        else _wishlistIsEmptyOrNull.postValue(false)
    }

    suspend fun changeCurrentQueryFromInputFlov(inputQuery: String) {
        currentQueryFromInputFlov.emit(inputQuery)
    }

    val filmFlow: Flow<PagingData<MovieSimple>>
        get() {
            val currentQ = currentQueryFromInputFlov.value
            return Pager(
                PagingConfig(
                    pageSize = Constance.PAGE_SIZE,
                    prefetchDistance = Constance.PREFETCH_DISTANCE,
                    enablePlaceholders = false
                )
            ){
                MoviesListPagingSource(getSearchedMoviesByName, currentQ)
            }.flow.cachedIn(viewModelScope)
        }

    suspend fun addSingleMovieToWishlist(movie: MovieSimple) {
        saveSingleMovieToWishlist.execute(movie)
    }

    // if user sviped for delete nut then pressed no in alert dialog
    suspend fun fakeDeleteOfItemMovie(movie: MovieSimple) {
        deleteSingleMovieFromWishlist.execute(movie.id)
        saveSingleMovieToWishlist.execute(movie)
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


