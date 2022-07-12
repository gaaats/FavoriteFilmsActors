package com.example.favoritefilmsactors.data.repo

import android.util.Log
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieLocalDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.presentation.vievmodels.ResourceVrap
import com.example.favoritefilmsactors.utils.CustomMovieException
import com.example.favoritefilmsactors.utils.constance.Constance
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {

    lateinit var movieListItemNetEntity: List<MovieItemNetEntity>
    lateinit var movieListItemDBEntity: List<MovieItemEntityDB>
    lateinit var movieListItemSimple: List<MovieSimple>


    override suspend fun getMovies(pageIndex: Int): ResourceVrap<List<MovieSimple>> {

        val response = movieRemoteDataSource.downloadMoviesFromNet(pageIndex)
        try {
            if (response.isSuccessful && response.body() != null) {
                Log.d(Constance.TAG, "good")
                return ResourceVrap.Success(response.body()!!.movies.map {
                    it.convertToSimpleEntity()
                })
            }
            return ResourceVrap.Error(
                exception = CustomMovieException(
                    response.code().toString() + " " + response.errorBody().toString()
                )
            )
        } catch (e: Exception) {
            return ResourceVrap.Error(CustomMovieException(errorMessage = e.message.toString()))
        }


//        val preResult = getMoviesFromAPI(pageIndex)
//        if ()

//        return getMoviesFromCache(pageIndex)
    }

    override suspend fun getSearchedMoviesByNameUseCase(query: String): List<MovieSimple> {
        val result = movieRemoteDataSource.getSearchedMoviesByName(query)
//        if (result.isSuccessful){
        return result.body()?.movies?.map {
            it.convertToSimpleEntity()
        }
            ?: throw RuntimeException("error or NULL in MoviesRepositoryImpl-getSearchedMoviesByNameUseCase")
//        } else {
//            throw RuntimeException("loading failed MoviesRepositoryImpl-getSearchedMoviesByNameUseCase")
//        }
    }

//    override suspend fun updateMovies(): List<MovieItemNetEntity>? {
//        return null
//        val nevUpdatedList = getMoviesFromAPI(pageIndex = 1)
//        movieLocalDataSource.deleteAllMoviesFromDB()
//        movieLocalDataSource.saveMoviesToDB(nevUpdatedList.map {
//            it.convertToDBEntity()
//        })
//        // NEED to make fun convert from all classes or make separate class
//
//        movieCacheDataSource.saveMoviesToCache(nevUpdatedList.map {
//            it.convertToDBEntity().convertToSimpleEntity()
//        })
//        return nevUpdatedList
//    }

    override suspend fun getListOfImages(imageId: Int): List<ImagesItem> {
        // make normal

        val result = movieRemoteDataSource.downloadImagesFromNet(imageId)
        val listForReturn = mutableListOf<ImagesItem>()
        if (result.isSuccessful) {
            Log.d(Constance.TAG, "good load images")
            val prepare = result.body()?.posters!!
            for (elemnt in prepare) {
                Log.d(Constance.TAG, "getListOfImages: ${elemnt.filePath}")
                if (prepare.indexOf(elemnt) % 5 == 0) {
                    ImagesItem(filePath = Constance.BASE_PATH_IMAGE + elemnt.filePath).also {
                        if (listForReturn.size <= MAX_SIZE_IMAGE_LIST) {
                            // add just 4 elements
                            listForReturn.add(it)
                        } else return listForReturn.toList()
                    }
                }
            }
        } else Log.d(Constance.TAG, "ERROR in load images")
        return listForReturn.toList()
    }

    override suspend fun saveSingleMovieToWishlist(movie: MovieItemEntityDB) {
        movieLocalDataSource.saveSingleMovieToDB(movie)
    }


//    suspend fun getMoviesFromAPI(pageIndex: Int): ResourceVrap<List<MovieItemNetEntity>> {
//        Log.d(Constance.TAG, "getMoviesFromAPI")
//        val response = movieRemoteDataSource.downloadMoviesFromNet(pageIndex)
//        try {
//            if (response.isSuccessful && response.body() != null) {
//                Log.d(Constance.TAG, "good")
//                return ResourceVrap.Success(response.body()!!.movies)
//            }
//            return ResourceVrap.Error(CustomMovieException(response.code().toString() + response.errorBody().toString()))
//        } catch (e: Exception) {
//            return ResourceVrap.Error(CustomMovieException(e.message!!))
//        }
//        movieListItemNetEntity = response.body()!!.movies
//        try {
//            val response = movieRemoteDataSource.downloadMoviesFromNet()
////            if (response.isSuccessful){
//                movieListItemNetEntity = response.body()!!.movies
////            }
//        }
//        catch (e:Exception){
//            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromAPI")
//        }
//        return movieListItemNetEntity
//    }

    override suspend fun getMoviesFromDataBase(): List<MovieItemEntityDB> {
        Log.d(Constance.TAG, "getMoviesFromDataBase - Wishlist")
        try {
            movieListItemDBEntity = movieLocalDataSource.getMoviesFromDB()
        } catch (e: Exception) {
            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromDataBase")
        }
        return movieListItemDBEntity
    }

    override suspend fun deleteSingleMovieFromWishlist(movieId: Int) {
        movieLocalDataSource.deleteSingleMovieFromDB(movieId)
    }

    suspend fun getMoviesFromCache(pageIndex: Int): List<MovieSimple> {
        Log.d(Constance.TAG, "getMoviesFromCache")
        try {
            movieListItemSimple = movieCacheDataSource.getMoviesFormCache()
        } catch (e: Exception) {
            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromCache")
        }
        //  todo
//        if (movieListItemSimple.size <= 0) {
//            movieListItemSimple = getMoviesFromAPI(pageIndex).map {
//                it.convertToSimpleEntity()
//            }

//            movieListItemSimple = getMoviesFromDataBase().map {
//                it.convertToSimpleEntity()
//            }
        movieCacheDataSource.saveMoviesToCache(movieListItemSimple)
        return movieListItemSimple
    }

    companion object {
        private const val MAX_SIZE_IMAGE_LIST = 4
    }
}