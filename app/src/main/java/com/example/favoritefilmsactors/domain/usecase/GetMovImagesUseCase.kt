package com.example.favoritefilmsactors.domain.usecase

import com.example.favoritefilmsactors.data.room.entity.images.ImagesItem
import com.example.favoritefilmsactors.domain.repo.MovieRepository
import com.example.favoritefilmsactors.utils.ResourceVrap
import javax.inject.Inject

class GetMovImagesUseCase @Inject constructor(private val repository: MovieRepository) {
    suspend operator fun invoke(id: Int): ResourceVrap<List<ImagesItem>> {
        return repository.getListOfImages(id)
    }
}