package com.app.movies.data.model

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val error: DataError, val errorBody: String? = null) : ApiResult<Nothing>()
}