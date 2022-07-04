package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.domain.entity.MovieSimple

class MovieCachDataSourceImpl: MovieCacheDataSource {

    companion object{
        val cacheMovieList = ArrayList<MovieSimple>()
    }
    override suspend fun getMoviesFormCache(): List<MovieSimple> {
        return cacheMovieList
    }

    override suspend fun saveMoviesToCache(movies: List<MovieSimple>) {
        cacheMovieList.apply {
            this.clear()
            ArrayList(movies)
        }
    }
}