package com.app.movies.domain.util

import android.content.Context
import com.app.movies.R
import com.app.movies.data.model.ApiResult
import com.app.movies.data.model.DataError
import com.app.movies.domain.model.DomainError
import com.app.movies.domain.model.DomainResult
import com.app.movies.util.ResourceProvider
import com.app.movies.util.ResourceProviderImpl
import com.google.gson.Gson

class ResultWrapperImpl(appContext: Context) : ResultWrapper,
    ResourceProvider by ResourceProviderImpl(appContext) {

    override val unknownError: DomainError = DomainError.UnknownError(string(R.string.error_occurred))

    override suspend fun <T, R> wrap(
        block: suspend () -> ApiResult<T>,
        mapper: suspend (data: T) -> R,
        errorHandler: (error: DataError, errorBody: String?) -> DomainError
    ): DomainResult<R> =
        when (val result = block()) {
            is ApiResult.Success -> DomainResult.Success(mapper(result.data))
            is ApiResult.Error -> DomainResult.Error(errorHandler(result.error, result.errorBody))
        }

    override fun defaultErrorHandler(error: DataError, errorBody: String?): DomainError =
        when (error) {
            DataError.Network -> DomainError.NetworkError(string(R.string.network_error))
            DataError.Unauthorized, DataError.Forbidden -> DomainError.Unauthorized(string(R.string.forbidden_error))
            else -> unknownError
        }

    override fun <T> serializedBody(
        clazz: Class<T>,
        error: DataError,
        body: String?,
        block: (errorBody: T) -> DomainError
    ): DomainError =
        try {
            val errorBody = Gson().fromJson(body, clazz)

            block(errorBody)
        } catch (e: Exception) {
            defaultErrorHandler(error = error, errorBody = body)
        }

    override fun parseError(errors: List<String>): DomainError {
        errors.forEach {
            serverError[it]?.let { msg ->
                return DomainError.InvalidData(msg)
            }
        }

        return unknownError
    }


    private val serverError: Map<String, String> = mapOf(
        string(R.string.test_error_server) to string(R.string.test_error)
    )
}