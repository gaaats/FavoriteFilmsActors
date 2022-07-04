package com.example.favoritefilmsactors.data.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.favoritefilmsactors.data.room.dao.ActorsDao
import com.example.favoritefilmsactors.data.room.dao.MoviesDao
import com.example.favoritefilmsactors.data.room.dao.TvShovDao
import com.example.favoritefilmsactors.data.room.entity.ActorItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.MovieItemEntityDB
import com.example.favoritefilmsactors.data.room.entity.TvShowItemEntityDB

@Database(
    entities = [MovieItemEntityDB::class, ActorItemEntityDB::class, TvShowItemEntityDB::class],
    version = 1,
    exportSchema = false
)
abstract class TMDbDataBase : RoomDatabase() {

    abstract fun getActorsDAO(): ActorsDao
    abstract fun getMoviesDao(): MoviesDao
    abstract fun getTvShovDao(): TvShovDao

    companion object{

        private var INSTANCE: TMDbDataBase? = null
        private val LOCK = Any()
        private const val DATA_BASE_NAME = "mov_act_tv.db"

        fun getDataBase(application: Application): TMDbDataBase{
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    TMDbDataBase::class.java,
                    DATA_BASE_NAME
                )
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}