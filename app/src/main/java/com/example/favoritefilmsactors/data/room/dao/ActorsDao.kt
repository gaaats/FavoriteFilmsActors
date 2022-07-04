package com.example.favoritefilmsactors.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favoritefilmsactors.data.room.entity.ActorItemEntityDB
import kotlinx.coroutines.flow.Flow


@Dao
interface ActorsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveActors(actors: List<ActorItemEntityDB>)

    @Query("DELETE FROM ")
    suspend fun deleteAllActors()

    @Query("SELECT * FROM movies_table")
    suspend fun getActors(): List<ActorItemEntityDB>
}