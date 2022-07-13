package com.example.favoritefilmsactors.data.repo.datasource.nevs

import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.nevs.NevsApiResponse
import com.example.favoritefilmsactors.utils.SimpleResponse

interface NevsRemoteDataSource {
    suspend fun downloadNevsFromApi(pageIndex:Int, query:String): SimpleResponse<NevsApiResponse>
}