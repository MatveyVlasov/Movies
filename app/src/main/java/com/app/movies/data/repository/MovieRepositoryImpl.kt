package com.app.movies.data.repository

import com.app.movies.data.model.ApiResult
import com.app.movies.data.model.movie.MoviesDto
import com.app.movies.data.service.MovieService
import com.app.movies.data.util.RequestWrapper
import com.app.movies.data.util.RequestWrapperImpl
import com.app.movies.di.qualifiers.IODispatcher
import com.app.movies.domain.repository.MovieRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    @IODispatcher dispatcher: CoroutineDispatcher,
    private val movieService: MovieService
) : MovieRepository, RequestWrapper by RequestWrapperImpl(dispatcher) {

    override suspend fun getPopularMovies(page: Int): ApiResult<MoviesDto> =
        wrap {
            movieService.getPopularMovies(page = page)
        }

    override suspend fun searchMovies(query: String): ApiResult<MoviesDto> =
        wrap {
            movieService.searchMovies(query = query)
        }
}