package com.example.favoritefilmsactors.data.room

import android.app.Application
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favoritefilmsactors.data.room.dao.MoviesDao
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB

@Database(
    entities = [MovieItemEntityDB::class],
    version = 2,
    exportSchema = false
)
abstract class TMDbDataBase() : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    companion object {

        private var INSTANCE: TMDbDataBase? = null
        private val LOCK = Any()
        private const val DATA_BASE_NAME = "mov_act_tv.db"

        fun getDataBase(application: Application): TMDbDataBase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    TMDbDataBase::class.java,
                    DATA_BASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}