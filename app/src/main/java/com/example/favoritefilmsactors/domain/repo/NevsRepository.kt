package com.example.favoritefilmsactors.domain.repo

import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.utils.ResourceVrap

interface NevsRepository {

    suspend fun getNevs(pageIndex: Int, query: String): ResourceVrap<List<ArticleUIModel>>
}