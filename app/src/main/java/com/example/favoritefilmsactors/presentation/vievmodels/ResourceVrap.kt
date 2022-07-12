package com.example.favoritefilmsactors.presentation.vievmodels

// A generic class that contains data and status about loading this data.
sealed class ResourceVrap<T>(
    val data: T? = null,
    val exception: Throwable? = null,
) {
    class Success<T>(data: T) : ResourceVrap<T>(data)
    class Loading<T>(data: T? = null) : ResourceVrap<T>(data)
    class Error<T>(exception: Throwable, data: T? = null) : ResourceVrap<T>(exception = exception, data = data )
    class Empty<T>: ResourceVrap<T>()
}