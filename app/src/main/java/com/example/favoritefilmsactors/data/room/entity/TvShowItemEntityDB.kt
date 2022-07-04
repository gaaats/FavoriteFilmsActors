package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.constance.Constance
import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShowItemNetEntity
import com.example.favoritefilmsactors.domain.entity.TvShovSimple

@Entity(tableName = "tv_shovs_table")
data class TvShowItemEntityDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    val overview: String?,
    val popularity: Double?,
) {
    fun convertToRemoteEntity() = TvShowItemNetEntity(
        id, name, overview, popularity
    )

    fun convertToSimpleEntity() = TvShovSimple(
        id,
        name ?: Constance.DEFAULT_VALUE,
        overview ?: Constance.DEFAULT_VALUE,
        popularity ?: Constance.DEFAULT_POPULARITY
    )
}