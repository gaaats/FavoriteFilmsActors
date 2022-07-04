package com.example.favoritefilmsactors.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import kotlinx.coroutines.flow.Flow


@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMovie(movies: List<MovieItemEntityDB>)

    @Query("DELETE FROM movies_table")
    suspend fun deleteAllMovies()

    @Query("SELECT * FROM movies_table")
    fun getMovies(): Flow<List<MovieItemEntityDB>>
}