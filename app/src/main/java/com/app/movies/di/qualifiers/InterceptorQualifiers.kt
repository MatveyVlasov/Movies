package com.app.movies.di.qualifiers

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class LoggingInterceptorQualifier

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuthInterceptorQualifier
