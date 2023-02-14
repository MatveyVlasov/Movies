package com.app.movies.data.model

sealed class DataError {
    data class ServerError(val code: Int) : DataError()
    data class ClientError(val code: Int) : DataError()

    object Unauthorized : DataError()
    object BadRequest : DataError()
    object Forbidden : DataError()
    object NotFound : DataError()
    object Network : DataError()
    object Unknown : DataError()
}