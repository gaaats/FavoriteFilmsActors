package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShowItemNetEntity
import com.example.favoritefilmsactors.domain.TvShovsRepository

class GetTVShovsUseCase (private val repository: TvShovsRepository) {

    suspend operator fun invoke(): List<TvShowItemNetEntity>? {
        return repository.getTvShovs()
    }
}