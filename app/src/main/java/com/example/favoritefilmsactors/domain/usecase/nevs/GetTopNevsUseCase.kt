package com.example.favoritefilmsactors.domain.usecase.nevs

import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.domain.repo.NevsRepository
import com.example.favoritefilmsactors.utils.ResourceVrap
import javax.inject.Inject

class GetTopNevsUseCase @Inject constructor(private val nevsRepository: NevsRepository) {

    suspend fun execute(pageIndex: Int =1, query: String = "Ukraine"): ResourceVrap<List<ArticleUIModel>>{
        return nevsRepository.getNevs(pageIndex = pageIndex, query = query)
    }
}