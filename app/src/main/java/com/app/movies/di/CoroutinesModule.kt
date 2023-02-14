package com.app.movies.di

import com.app.movies.di.qualifiers.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesModule {

    @Provides
    @IODispatcher
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}