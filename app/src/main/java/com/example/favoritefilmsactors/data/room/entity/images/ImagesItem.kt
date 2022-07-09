package com.example.favoritefilmsactors.data.room.entity.images

import kotlin.random.Random

data class ImagesItem(
    val id:Int = Random.nextInt(1,1000),
    val filePath: String
) {

}
