package com.example.favoritefilmsactors.data.remote.models.tvshov


import com.example.favoritefilmsactors.data.room.entity.TvShowItemEntityDB
import com.google.gson.annotations.SerializedName

data class TvShowItemNetEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
){
    fun convertToDBEntity() = TvShowItemEntityDB(
        id, name, overview, popularity
    )
}