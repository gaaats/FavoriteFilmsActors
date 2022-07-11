package com.example.favoritefilmsactors.data.repo.datasource.impl.movie

import com.example.favoritefilmsactors.data.repo.datasource.movie.MovieCacheDataSource
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import javax.inject.Inject

class MovieCachDataSourceImpl @Inject constructor (): MovieCacheDataSource {

    companion object{
        val cacheMovieList = mutableListOf<MovieSimple>()
    }
    override suspend fun getMoviesFormCache(): List<MovieSimple> {
        return cacheMovieList
    }

    override suspend fun saveMoviesToCache(movies: List<MovieSimple>) {
        cacheMovieList.clear()
        movies.forEach {
            cacheMovieList.add(it)
        }
//        cacheMovieList = movies.toMutableList()
//        cacheMovieList.apply {
//            this.clear()
//            ArrayList(movies)
//        }
    }
}