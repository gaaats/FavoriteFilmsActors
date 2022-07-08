package com.example.favoritefilmsactors.data.remote.models.movie.images


import com.google.gson.annotations.SerializedName

data class ImagesList(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("posters")
    val posters: List<Poster?>?
)