package com.example.favoritefilmsactors.utils

sealed class CurrentState
    (
    val data: Any? = null,
    val exception: Throwable? = null
) {

    class Success<T>(data: T) : ResourceVrap<T>(data)
    object Loading : CurrentState()
    object UserOpenedSearchViev : CurrentState()
    object UserClosedSearchViev : CurrentState()
    class Error(exception: CustomMovieException) : CurrentState(exception)
    object LoadingIsFinishedOrEmpty : CurrentState()

}