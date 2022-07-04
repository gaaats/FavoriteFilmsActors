package com.example.favoritefilmsactors.data.remote.models.actors


import com.google.gson.annotations.SerializedName

data class ActorsList(
    @SerializedName("results")
    val results: List<ActorItem>,
)