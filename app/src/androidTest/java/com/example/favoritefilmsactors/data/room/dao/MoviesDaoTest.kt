package com.example.favoritefilmsactors.data.room.dao

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.favoritefilmsactors.data.room.TMDbDataBase
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MoviesDaoTest {

    @get:Rule val hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var moviesDao: MoviesDao
    private lateinit var database: TMDbDataBase
    private lateinit var application: Application

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            application,
            TMDbDataBase::class.java
        ).build()
        moviesDao = database.getMoviesDao()
    }

    @Test
    fun saveMoviesTest() = runBlocking {
        val movies = listOf(
            MovieSimple(1, "harry potter", "poster", "data", "potter title"),
            MovieSimple(2, "star vars", "poster", "data", "star vars title"),
            MovieSimple(3, "vlastelin kolets", "poster", "data", "vlastelin kolets title")
        ).map {
            it.convertToDataBaseEntity()
        }
        moviesDao.saveMovie(movies)
        val result = moviesDao.getMovies()
        Truth.assertThat(result).isEqualTo(movies)
    }


    @After
    fun tearDown() {
        database.close()
    }
}