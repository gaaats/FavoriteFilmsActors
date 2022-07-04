package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.constance.Constance
import com.example.favoritefilmsactors.data.remote.models.actors.ActorItemNetEntity
import com.example.favoritefilmsactors.domain.entity.ActorSimple

@Entity(tableName = "actors_table")
data class ActorItemEntityDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val popularity: Double?,
    @ColumnInfo("profile_path")
    val profilePath: String?
) {
    fun convertToRemoteEntity() = ActorItemNetEntity(
        id, name, popularity, profilePath
    )

    fun convertToSimpleEntity() = ActorSimple(
        id, name ?: "default", popularity ?: Constance.DEFAULT_POPULARITY, profilePath ?: "default"
    )
}