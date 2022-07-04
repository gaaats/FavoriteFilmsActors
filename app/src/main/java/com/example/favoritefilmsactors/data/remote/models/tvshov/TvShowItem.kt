package com.example.favoritefilmsactors.data.remote.models.tvshov


import com.example.favoritefilmsactors.data.room.entity.TvShowItemEntityDB
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
){
    fun convertToDBEntity() = TvShowItemEntityDB(
        id, name, originCountry, overview, popularity
    )
}