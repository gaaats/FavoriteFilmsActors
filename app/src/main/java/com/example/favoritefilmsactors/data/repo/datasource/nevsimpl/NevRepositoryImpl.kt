package com.example.favoritefilmsactors.data.repo.datasource.nevsimpl

import com.example.favoritefilmsactors.data.repo.datasource.nevs.NevsRemoteDataSource
import com.example.favoritefilmsactors.domain.entity.ArticleUIModel
import com.example.favoritefilmsactors.domain.repo.NevsRepository
import com.example.favoritefilmsactors.utils.ResourceVrap
import javax.inject.Inject

class NevRepositoryImpl @Inject constructor(
    private val nevsRemoteDataSource: NevsRemoteDataSource,
): NevsRepository {



    override suspend fun getNevs(
        pageIndex: Int,
        query: String
    ): ResourceVrap<List<ArticleUIModel>> {
        val response = nevsRemoteDataSource.downloadNevsFromApi(pageIndex = pageIndex, query = query)
        response.exception?.let {
            return ResourceVrap.Error(exception = it)
        }
        if (response.body.articles.isNullOrEmpty()) {
            return ResourceVrap.Error(RuntimeException("list inside is empty"))
        }
        val preRes = response.body!!.articles!!

        return ResourceVrap.Success(preRes.map {
            it!!.convertFromApiModelToUi()
        })
    }
}