package com.example.favoritefilmsactors.domain

import com.example.favoritefilmsactors.data.remote.models.actors.ActorItemNetEntity

interface ActorsRepository {

    suspend fun getActors():List<ActorItemNetEntity>?
    suspend fun updateActors():List<ActorItemNetEntity>?
}