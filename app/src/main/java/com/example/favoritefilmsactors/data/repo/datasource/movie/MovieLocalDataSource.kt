package com.example.favoritefilmsactors.data.repo.datasource.movie

import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB

interface MovieLocalDataSource {

    suspend fun getMoviesFromDB():List<MovieItemEntityDB>
    suspend fun saveMoviesToDB(listOfMovies: List<MovieItemEntityDB>)
    suspend fun deleteAllMoviesFromDB()
}