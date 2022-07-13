package com.example.favoritefilmsactors.utils


//You should have a sealed class to handle for different type of event. For example, Success, Error or Loading. Here is some of the example that fits your usecases.

enum class ApiStatus{
    SUCCESS,
    ERROR,
    LOADING,
    EMPTY
}  // for your case might be simplify to use only sealed class

sealed class CurrentResult <out T> (val status: ApiStatus, val data: T?, val message:String?) {

    fun getNotNulldata(): T{
        return data!!
    }

    data class Success<out R>(val _data: R?): CurrentResult<R>(
        status = ApiStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: String): CurrentResult<Nothing>(
        status = ApiStatus.ERROR,
        data = null,
        message = exception
    )

    data class Loading(val isLoading: Boolean): CurrentResult<Nothing>(
        status = ApiStatus.LOADING,
        data = null,
        message = null
    )
    object Empty: CurrentResult<Nothing>(
        status = ApiStatus.EMPTY,
        data = null,
        message = null
    )
}