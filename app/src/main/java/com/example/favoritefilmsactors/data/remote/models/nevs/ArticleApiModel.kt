package com.example.favoritefilmsactors.data.remote.models.nevs


import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.google.gson.annotations.SerializedName

data class ArticleApiModel(
    @SerializedName("author")
    val author: String?,
    @SerializedName("content")
    val content: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("publishedAt")
    val publishedAt: String?,
    @SerializedName("source")
    val source: Source?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("urlToImage")
    val urlToImage: String?
) {
    //TODO: make it normal mapper

    fun convertFromApiModelToUi(): ArticleUIModel {
        return ArticleUIModel(
            author = author?: "default author",
            content = content?: "default content",
            description = description ?: "default description",
            publishedAt = publishedAt?: "default publishedAt",
            source = source!!.name?: "default source name",
            title = title?: "default title",
            url = url?: "default url",
            urlToImage = urlToImage?: "no",
        )
    }
}


