package com.app.movies.data.model.movie

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("poster_path")
    val image: String?
)