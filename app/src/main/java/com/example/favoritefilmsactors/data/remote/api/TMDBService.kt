package com.example.favoritefilmsactors.data.remote.api

import com.example.favoritefilmsactors.data.remote.models.actors.ActorsList
import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShovList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Response<MovieList>

    @GET("tv/popular")
    suspend fun getPopularTVShovs(@Query("api_key") apiKey: String): Response<TvShovList>

    @GET("person/popular")
    suspend fun getPopularActors(@Query("api_key") apiKey: String): Response<ActorsList>
}