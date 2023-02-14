package com.app.movies.domain.mapper

import com.app.movies.data.model.movie.MovieDto
import com.app.movies.data.model.movie.MoviesDto
import com.app.movies.domain.model.MovieModel
import com.app.movies.util.toImageUrl

fun MoviesDto.toDomain() = movies.map { it.toDomain() }

fun MovieDto.toDomain() =
    MovieModel(
        id = id,
        title = title,
        image = image?.toImageUrl().orEmpty()
    )