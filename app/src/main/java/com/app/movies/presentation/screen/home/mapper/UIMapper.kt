package com.app.movies.presentation.screen.home.mapper

import com.app.movies.domain.model.MovieModel
import com.app.movies.presentation.screen.home.model.MovieDelegateItem

fun MovieModel.toMovieItem() =
    MovieDelegateItem(
        id = id,
        title = title,
        image = image
    )