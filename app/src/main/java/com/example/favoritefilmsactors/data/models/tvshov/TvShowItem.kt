package com.example.favoritefilmsactors.data.models.tvshov


import com.google.gson.annotations.SerializedName

data class TvShowItem(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin_country")
    val originCountry: List<String?>?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
)