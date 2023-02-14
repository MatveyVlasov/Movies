package com.app.movies.di

import com.app.movies.data.repository.MovieRepositoryImpl
import com.app.movies.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindMovieRepository(impl: MovieRepositoryImpl): MovieRepository
}