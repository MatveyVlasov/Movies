package com.app.movies.domain.util

import com.app.movies.data.model.ApiResult
import com.app.movies.data.model.DataError
import com.app.movies.domain.model.DomainError
import com.app.movies.domain.model.DomainResult

interface ResultWrapper {
    val unknownError: DomainError

    suspend fun <T, R> wrap(
        block: suspend () -> ApiResult<T>,
        mapper: suspend (data: T) -> R,
        errorHandler: (error: DataError, errorBody: String?) -> DomainError = this::defaultErrorHandler
    ): DomainResult<R>

    fun defaultErrorHandler(error: DataError, errorBody: String?): DomainError

    fun <T> serializedBody(
        clazz: Class<T>,
        error: DataError,
        body: String?,
        block: (errorBody: T) -> DomainError
    ): DomainError

    fun parseError(errors: List<String>): DomainError
}