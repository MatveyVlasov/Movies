package com.app.movies.data.service

import com.app.movies.data.model.movie.MoviesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {
    @GET("movie/popular/")
    suspend fun getPopularMovies(
        @Query("page")
        page: Int
    ): Response<MoviesDto>
}
