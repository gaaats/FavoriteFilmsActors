package com.example.favoritefilmsactors.data.repo

import android.util.Log
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.data.remote.models.movie.images.Poster
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieLocalDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.repo.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.ResourceVrap
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
        response.exception?.let {
            return ResourceVrap.Error(exception = it)
        }
        return ResourceVrap.Success(response.body.movies.map {
            it.convertToSimpleEntity()
        })
    }

    override suspend fun getSearchedMoviesByNameUseCase(query:String, pageIndex: Int): ResourceVrap<List<MovieSimple>> {
        val response = movieRemoteDataSource.getSearchedMoviesByName(
            querySearch = query,
            pageIndex = pageIndex
        )
        response.exception?.let {
            return ResourceVrap.Error(exception = it)
        }
        return ResourceVrap.Success(response.body.movies.map {
            it.convertToSimpleEntity()
        })
    }

    override suspend fun getListOfImages(imageId: Int): ResourceVrap<List<ImagesItem>> {

        val response = movieRemoteDataSource.downloadImagesFromNet(imageId)
        response.exception?.let {
            return ResourceVrap.Error(exception = it)
        }
        val listForReturn = mutableListOf<ImagesItem>()
        val prepare = response.body.posters?.let {
            it.filter { poster: Poster ->
                it.indexOf(poster) % LOAD_EVERY_NUMBER == 0
            }.map { poster2: Poster ->
                ImagesItem(filePath = Constance.BASE_PATH_IMAGE + poster2.filePath)
            }
        }!!
        for (element in prepare){
            if (listForReturn.size <= MAX_SIZE_IMAGE_LIST) {
                // add just 4 elements
                listForReturn.add(element)
            } else return ResourceVrap.Success(listForReturn.toList())
        }
        return ResourceVrap.Success(listForReturn)
    }

    override suspend fun saveSingleMovieToWishlist(movie: MovieItemEntityDB) {
        movieLocalDataSource.saveSingleMovieToDB(movie)
    }
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

        // there are to many images
        private const val LOAD_EVERY_NUMBER = 5
    }
}