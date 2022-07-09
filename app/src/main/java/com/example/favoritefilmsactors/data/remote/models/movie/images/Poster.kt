package com.example.favoritefilmsactors.data.remote.models.movie.images


import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("file_path")
    val filePath: String
)