package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.data.remote.models.actors.ActorItem

@Entity(tableName = "actors_table")
data class ActorItemEntityDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val popularity: Double?,
    @ColumnInfo("profile_path")
    val profilePath: String?
){
    fun convertToRemoteEntity()=ActorItem(
        id, name, popularity, profilePath
    )
}