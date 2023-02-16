package com.app.movies.domain.repository

import com.app.movies.data.model.ApiResult
import com.app.movies.data.model.movie.MoviesDto

interface MovieRepository {

    suspend fun getPopularMovies(page: Int): ApiResult<MoviesDto>

    suspend fun searchMovies(query: String): ApiResult<MoviesDto>
}