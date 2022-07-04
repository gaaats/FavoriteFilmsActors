package com.example.favoritefilmsactors.data.remote.models.actors


import com.example.favoritefilmsactors.data.room.entity.ActorItemEntityDB
import com.google.gson.annotations.SerializedName

data class ActorItemNetEntity(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("profile_path")
    val profilePath: String?
){
    fun convertToDBEntity() = ActorItemEntityDB(
        id, name, popularity, profilePath
    )
}