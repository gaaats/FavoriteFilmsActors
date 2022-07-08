package com.example.favoritefilmsactors.data.remote.api

import com.example.favoritefilmsactors.data.remote.models.actors.ActorsList
import com.example.favoritefilmsactors.data.remote.models.movie.MovieList
import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShovList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    companion object {
//        const val BASE_URL = "https://api.themoviedb.org"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "450fc2a680aa5693bf2e69ce39671d03"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = API_KEY): Response<MovieList>

    @GET("movie/{movie_id}/images")
    suspend fun getMoviesImages(
        @Query("api_key") apiKey: String = API_KEY,
        @Path("movie_id") movieId:Int
    ): Response<MovieList>

    @GET("tv/popular")
    suspend fun getPopularTVShovs(@Query("api_key") apiKey: String = API_KEY): Response<TvShovList>

    @GET("person/popular")
    suspend fun getPopularActors(@Query("api_key") apiKey: String = API_KEY): Response<ActorsList>
}