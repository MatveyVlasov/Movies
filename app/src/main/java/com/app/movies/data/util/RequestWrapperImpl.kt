package com.app.movies.data.util

import com.app.movies.data.model.ApiResult
import com.app.movies.data.model.DataError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection

class RequestWrapperImpl(private val ioDispatcher: CoroutineDispatcher) : RequestWrapper {
    override suspend fun <T> wrap(
        request: suspend () -> Response<T>
    ): ApiResult<T> =
        withContext(ioDispatcher) {
            try {
                handleResponse(response = request(), mapper = { it })
            } catch (t: Throwable) {
                handleRequestException(t)
            }
        }

    override suspend fun <T, R> wrap(
        request: suspend () -> Response<T>,
        mapper: (T) -> R
    ): ApiResult<R> =
        withContext(ioDispatcher) {
            try {
                handleResponse(response = request(), mapper = mapper)
            } catch (t: Throwable) {
                handleRequestException(t)
            }
        }

    private fun <T, R> handleResponse(response: Response<T>, mapper: (T) -> R): ApiResult<R> =
        try {
            if (response.isSuccessful) {
                ApiResult.Success(mapper(checkNotNull(response.body())))
            } else {
                handleErrorResponse(response)
            }
        } catch (e: Throwable) {
            ApiResult.Error(error = DataError.Unknown)
        }

    private fun <T> handleErrorResponse(response: Response<T>): ApiResult.Error {
        val error = when (val code = response.code()) {
            HttpURLConnection.HTTP_UNAUTHORIZED -> DataError.Unauthorized
            HttpURLConnection.HTTP_BAD_REQUEST -> DataError.BadRequest
            HttpURLConnection.HTTP_FORBIDDEN -> DataError.Forbidden
            HttpURLConnection.HTTP_NOT_FOUND -> DataError.NotFound
            in CLIENT_ERROR_CODE_RANGE -> DataError.ClientError(code)
            in SERVER_ERROR_CODE_RANGE -> DataError.ServerError(code)
            else -> DataError.Unknown
        }

        return ApiResult.Error(error = error, errorBody = response.errorBody()?.string())
    }

    private fun handleRequestException(t: Throwable): ApiResult.Error =
        ApiResult.Error(
            error = when (t) {
                is IOException -> DataError.Network
                else -> DataError.Unknown
            }
        )

    companion object {
        private val CLIENT_ERROR_CODE_RANGE = 400..499
        private val SERVER_ERROR_CODE_RANGE = 500..526
    }
}