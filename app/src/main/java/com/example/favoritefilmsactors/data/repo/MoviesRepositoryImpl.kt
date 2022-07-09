package com.example.favoritefilmsactors.data.repo

import android.util.Log
import com.example.favoritefilmsactors.utils.constance.Constance
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.data.remote.models.movie.images.ImagesList
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieLocalDataSource
import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieRemoteDataSource
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.MovieRepository
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val movieLocalDataSource: MovieLocalDataSource,
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieCacheDataSource: MovieCacheDataSource
) : MovieRepository {

    lateinit var movieListItemNetEntity: List<MovieItemNetEntity>
    lateinit var movieListItemDBEntity: List<MovieItemEntityDB>
    lateinit var movieListItemSimple: List<MovieSimple>


    override suspend fun getMovies(): List<MovieSimple> {
        return getMoviesFromCache()
    }

    override suspend fun updateMovies(): List<MovieItemNetEntity>? {
        val nevUpdatedList = getMoviesFromAPI()
        movieLocalDataSource.deleteAllMoviesFromDB()
        movieLocalDataSource.saveMoviesToDB(nevUpdatedList.map {
            it.convertToDBEntity()
        })

        // NEED to make fun convert from all classes or make separate class

        movieCacheDataSource.saveMoviesToCache(nevUpdatedList.map {
            it.convertToDBEntity().convertToSimpleEntity()
        })
        return nevUpdatedList
    }

    override suspend fun getListOfImages(imageId: Int): List<ImagesItem> {
        // make normal

        val result = movieRemoteDataSource.downloadImagesFromNet(imageId)
        val listForReturn = mutableListOf<ImagesItem>()
        if (result.isSuccessful) {
            Log.d(Constance.TAG, "good load images")
            val prepare = result.body()?.posters!!
            for (elemnt in prepare){
                if (prepare.indexOf(elemnt) %15 == 0){
                    ImagesItem(filePath = Constance.BASE_PATH_IMAGE+elemnt.filePath).also {
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


    suspend fun getMoviesFromAPI(): List<MovieItemNetEntity> {
        Log.d(Constance.TAG, "getMoviesFromAPI")
        val response = movieRemoteDataSource.downloadMoviesFromNet()
        if (response.isSuccessful) {
            Log.d(Constance.TAG, "good")
        }
        movieListItemNetEntity = response.body()!!.movies
//        try {
//            val response = movieRemoteDataSource.downloadMoviesFromNet()
////            if (response.isSuccessful){
//                movieListItemNetEntity = response.body()!!.movies
////            }
//        }
//        catch (e:Exception){
//            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromAPI")
//        }
        return movieListItemNetEntity
    }

    suspend fun getMoviesFromDataBase(): List<MovieItemEntityDB> {
        Log.d(Constance.TAG, "getMoviesFromDataBase")
        try {
            movieListItemDBEntity = movieLocalDataSource.getMoviesFromDB()
        } catch (e: Exception) {
            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromDataBase")
        }
        if (movieListItemDBEntity.size <= 0) {
            movieListItemDBEntity = getMoviesFromAPI().map {
                it.convertToDBEntity()
            }
            movieLocalDataSource.saveMoviesToDB(movieListItemDBEntity)
        }
        return movieListItemDBEntity
    }

    suspend fun getMoviesFromCache(): List<MovieSimple> {
        Log.d(Constance.TAG, "getMoviesFromCache")
        try {
            movieListItemSimple = movieCacheDataSource.getMoviesFormCache()
        } catch (e: Exception) {
            Log.d(Constance.TAG, "there is error in MoviesRepositoryImpl -- getMoviesFromCache")
        }
        if (movieListItemSimple.size <= 0) {
            movieListItemSimple = getMoviesFromDataBase().map {
                it.convertToSimpleEntity()
            }
            movieCacheDataSource.saveMoviesToCache(movieListItemSimple)
        }
        return movieListItemSimple
    }
    companion object{
        private const val MAX_SIZE_IMAGE_LIST = 4
    }
}