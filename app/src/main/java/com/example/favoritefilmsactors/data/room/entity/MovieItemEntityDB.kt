package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.data.remote.models.movie.MovieItemNetEntity
import com.example.favoritefilmsactors.domain.entity.MovieSimple
import com.example.favoritefilmsactors.utils.constance.Constance as Const1

@Entity(tableName = "movies_table")
data class MovieItemEntityDB(

    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val overview: String?,
    @ColumnInfo("poster_path")
    val posterPath: String?,
    @ColumnInfo("release_date")
    val releaseDate: String?,
    @ColumnInfo("title")
    val title: String?
) {
    fun convertToRemoteEntity() = MovieItemNetEntity(
        id, overview, posterPath, releaseDate, title
    )

    fun convertToSimpleEntity() = MovieSimple(
        id,
        overview ?: Const1.DEFAULT_VALUE,
        posterPath ?: Const1.DEFAULT_VALUE,
        releaseDate ?: Const1.DEFAULT_VALUE,
        title?: Const1.DEFAULT_VALUE
    )
}