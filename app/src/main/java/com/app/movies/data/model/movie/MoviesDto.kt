package com.app.movies.data.model.movie

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("results")
    val movies: List<MovieDto>
)