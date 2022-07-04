package com.example.favoritefilmsactors.data.remote.models.movie


import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val movies: List<MovieItemNetEntity>,
)