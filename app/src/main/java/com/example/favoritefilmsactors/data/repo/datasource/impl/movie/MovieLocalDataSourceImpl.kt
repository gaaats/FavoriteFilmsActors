package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieLocalDataSource
import com.example.favoritefilmsactors.data.room.dao.MoviesDao
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : MovieLocalDataSource {
    override suspend fun getMoviesFromDB(): List<MovieItemEntityDB> {
        return moviesDao.getMovies()
    }

    override suspend fun saveMoviesToDB(listOfMovies: List<MovieItemEntityDB>) {
        CoroutineScope(Dispatchers.IO).launch {
            moviesDao.saveMovie(listOfMovies)
        }
    }

    override suspend fun deleteAllMoviesFromDB() {
        CoroutineScope(Dispatchers.IO).launch {
            moviesDao.deleteAllMovies()
        }
    }
}