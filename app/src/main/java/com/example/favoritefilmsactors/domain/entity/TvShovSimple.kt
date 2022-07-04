package com.example.favoritefilmsactors.domain.entity

import com.example.favoritefilmsactors.data.room.entity.TvShowItemEntityDB

data class TvShovSimple(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
) {
    fun convertToRemoteEntity() = TvShowItemEntityDB(
        id, name, overview, popularity
    )
}