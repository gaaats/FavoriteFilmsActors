package com.example.favoritefilmsactors.domain.entity

import com.example.favoritefilmsactors.data.remote.models.nevs.Source
import kotlin.random.Random

data class ArticleUIModel(
    val id: Int = Random.nextInt(from = 10, until = 50000),
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: String,
    val title: String,
    val url: String,
    val urlToImage: String,

)
