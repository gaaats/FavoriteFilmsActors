package com.example.favoritefilmsactors.data.remote.models.movie


import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.google.gson.annotations.SerializedName

data class MovieItem(

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
){
    fun convertToDBEntity() = MovieItemEntityDB(
        id, overview, posterPath, releaseDate, title
    )
}
