package com.example.favoritefilmsactors.domain.entity

import androidx.room.ColumnInfo
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB

data class MovieSimple(
    val id: Int,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String?
) {
    fun convertToDataBaseEntity() = MovieItemEntityDB(
        id, overview, posterPath, releaseDate, title
    )

}