package com.app.movies.domain.model

sealed interface DomainError {
    val message: String

    data class InvalidData(override val message: String) : DomainError

    data class Unauthorized(override val message: String) : DomainError
    data class NotFound(override val message: String) : DomainError
    data class NetworkError(override val message: String) : DomainError
    data class UnknownError(override val message: String) : DomainError
}