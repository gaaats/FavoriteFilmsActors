package com.example.favoritefilmsactors.data.room.entity


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favoritefilmsactors.data.remote.models.tvshov.TvShowItem

@Entity(tableName = "tv_shovs_table")
data class TvShowItemEntityDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String?,
    @ColumnInfo("origin_country")
    val originCountry: List<String?>?,
    val overview: String?,
    val popularity: Double?,
){
    fun convertToRemoteEntity()= TvShowItem(
        id, name, originCountry, overview, popularity
    )
}