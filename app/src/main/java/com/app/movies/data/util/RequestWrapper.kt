package com.app.movies.data.util

import com.app.movies.data.model.ApiResult
import retrofit2.Response

interface RequestWrapper {
    suspend fun <T> wrap(
        request: suspend () -> Response<T>,
    ): ApiResult<T>

    suspend fun <T, R> wrap(
        request: suspend () -> Response<T>,
        mapper: (T) -> R
    ): ApiResult<R>
}