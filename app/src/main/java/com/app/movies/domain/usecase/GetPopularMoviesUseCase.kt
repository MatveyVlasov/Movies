package com.app.movies.domain.usecase

import android.content.Context
import com.app.movies.domain.mapper.toDomain
import com.app.movies.domain.model.DomainResult
import com.app.movies.domain.model.MovieModel
import com.app.movies.domain.repository.MovieRepository
import com.app.movies.domain.util.ResultWrapper
import com.app.movies.domain.util.ResultWrapperImpl
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    @ApplicationContext context: Context,
    private val movieRepository: MovieRepository,
) : ResultWrapper by ResultWrapperImpl(context) {

    suspend operator fun invoke(page: Int): DomainResult<List<MovieModel>> =
        wrap(
            block = { movieRepository.getPopularMovies(page = page) },
            mapper = { it.toDomain() }
        )
}