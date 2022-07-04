package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItem

@Entity(tableName = "movies_table")
data class MovieItemEntityDB(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String?,
    @ColumnInfo("poster_path")
    val posterPath: String?,
    @ColumnInfo("release_date")
    val releaseDate: String?,
    @ColumnInfo("title")
    val title: String?
){
    fun convertToRemoteEntity()= MovieItem(
        id, overview, posterPath, releaseDate, title
    )
}