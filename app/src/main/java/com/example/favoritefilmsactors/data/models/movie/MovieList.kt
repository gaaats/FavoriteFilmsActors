package com.example.favoritefilmsactors.data.models.movie


import com.google.gson.annotations.SerializedName

data class MovieList(
    @SerializedName("results")
    val movies: List<MovieItem>,
)