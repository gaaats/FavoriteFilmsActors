package com.example.favoritefilmsactors.domain.entity

import com.example.favoritefilmsactors.data.room.entity.ActorItemEntityDB

data class ActorSimple(
    val id: Int,
    val name: String,
    val popularity: Double,
    val profilePath: String
){
    fun convertToDataBaseEntity() = ActorItemEntityDB(
        id, name, popularity, profilePath
    )
}