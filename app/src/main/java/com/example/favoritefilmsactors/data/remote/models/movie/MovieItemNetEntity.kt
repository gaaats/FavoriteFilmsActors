package com.example.favoritefilmsactors.data.remote.models.movie


import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.google.gson.annotations.SerializedName

data class MovieItemNetEntity(

    @SerializedName("id")
    val id: Int,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String?
) {
    fun convertToDBEntity() = MovieItemEntityDB(
        id, overview, posterPath, releaseDate, title
    )

    fun convertToSimpleEntity() = MovieSimple(
        id,
        overview ?: throw RuntimeException("null in MovieItemNetEntity"),
        posterPath ?: "poster default",
        releaseDate ?: "releaseDate default",
        title
    )

}
