package com.example.favoritefilmsactors.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favoritefilmsactors.data.room.entity.TvShowItemEntityDB
import kotlinx.coroutines.flow.Flow


@Dao
interface TvShovDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTvShows(tvShows: List<TvShowItemEntityDB>)

    @Query("DELETE FROM tv_shovs_table")
    suspend fun deleteAllTvShows()

    @Query("SELECT * FROM tv_shovs_table")
    suspend fun getTvShows(): Flow<List<TvShowItemEntityDB>>
}