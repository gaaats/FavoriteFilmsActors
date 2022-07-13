package com.example.favoritefilmsactors.data.repo.datasource.nevs

import com.example.favoritefilmsactors.data.remote.api.NewsAPIService
import com.example.favoritefilmsactors.data.remote.models.nevs.NevsApiResponse
import com.example.favoritefilmsactors.utils.SimpleResponse
import retrofit2.Response
import javax.inject.Inject

class NevsRemoteDataSourceImpl @Inject constructor(
    private val newsAPIService: NewsAPIService
) : NevsRemoteDataSource {

    override suspend fun downloadNevsFromApi(
        pageIndex: Int,
        query: String
    ): SimpleResponse<NevsApiResponse> {
        return safeApiCall { newsAPIService.getTopHeadlines(page = pageIndex, queryAbout = query) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}