package com.example.favoritefilmsactors.data.models.tvshov


import com.google.gson.annotations.SerializedName

data class TvShovList(
    @SerializedName("results")
    val listOfTvShow: List<TvShowItem>,
)