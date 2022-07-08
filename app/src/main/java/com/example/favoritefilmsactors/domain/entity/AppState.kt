package com.example.favoritefilmsactors.domain.entity

import kotlinx.coroutines.flow.Flow

sealed class AppState {

    object Load : AppState()
    object Empty : AppState()
    data class Success(var listFlov: Flow<List<MovieSimple>>) : AppState()
    data class Error(var errorInside: Throwable) : AppState()
}