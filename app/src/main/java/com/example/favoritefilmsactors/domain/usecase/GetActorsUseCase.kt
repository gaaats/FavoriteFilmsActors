package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.actors.ActorItemNetEntity
import com.example.favoritefilmsactors.domain.ActorsRepository

class GetActorsUseCase (private val repository: ActorsRepository) {

    suspend operator fun invoke(): List<ActorItemNetEntity>? {
        return repository.getActors()
    }
}