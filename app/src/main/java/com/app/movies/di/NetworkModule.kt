package com.app.movies.di

import com.app.movies.BuildConfig
import com.app.movies.data.service.MovieService
import com.app.movies.data.util.interceptor.AuthInterceptor
import com.app.movies.di.qualifiers.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    @AuthInterceptorQualifier
    abstract fun bindAuthInterceptor(impl: AuthInterceptor): Interceptor

    companion object {

        @Provides
        @DefaultConverterFactory
        fun provideDefaultConverterFactory(): Converter.Factory = GsonConverterFactory.create()

        @Provides
        @LoggingInterceptorQualifier
        fun provideLoggingInterceptor(): Interceptor = HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
        }

        @Provides
        @MoviesOkHttpClient
        fun provideEthnoComboOkHttpClient(
            @LoggingInterceptorQualifier loggingInterceptor: Interceptor,
            @AuthInterceptorQualifier authInterceptor: Interceptor
        ): OkHttpClient =
            OkHttpClient()
                .newBuilder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()

        @Provides
        @MoviesRetrofit
        fun provideEthnoComboRetrofit(
            @MoviesOkHttpClient okHttpClient: OkHttpClient,
            @DefaultConverterFactory converterFactory: Converter.Factory
        ): Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(converterFactory)
                .build()

        @Provides
        fun provideMovieService(@MoviesRetrofit retrofit: Retrofit): MovieService =
            retrofit.create(MovieService::class.java)

    }
}