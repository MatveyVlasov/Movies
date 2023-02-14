package com.app.movies.presentation.screen.home.model

import androidx.annotation.StringRes

sealed class HomeScreenEvent {

    data class ShowSnackbar(val message: String) : HomeScreenEvent()

    data class ShowSnackbarByRes(@StringRes val message: Int) : HomeScreenEvent()

    object Loading : HomeScreenEvent()
}