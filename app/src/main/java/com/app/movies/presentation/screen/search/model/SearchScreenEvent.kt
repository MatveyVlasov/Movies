package com.app.movies.presentation.screen.search.model

import androidx.annotation.StringRes

sealed class SearchScreenEvent {

    data class ShowSnackbar(val message: String) : SearchScreenEvent()

    data class ShowSnackbarByRes(@StringRes val message: Int) : SearchScreenEvent()

    object Loading : SearchScreenEvent()
}