package com.app.movies.domain.model

sealed class DomainResult<out T> {
    data class Success<out T>(val value: T) : DomainResult<T>()
    data class Error(val error: DomainError) : DomainResult<Nothing>()
}

inline fun <reified T> DomainResult<T>.onError(block: (error: DomainError) -> Unit): DomainResult<T> {
    if (this is DomainResult.Error) {
        block(this.error)
    }
    return this
}

inline fun <reified T> DomainResult<T>.onSuccess(block: (value: T) -> Unit): DomainResult<T> {
    if (this is DomainResult.Success) {
        block(value)
    }
    return this
}