package com.app.movies.presentation.screen.home.model

import com.app.movies.util.delegateadapter.DelegateAdapterItem

data class HomeScreenUIState(
    val movies: List<DelegateAdapterItem> = emptyList(),
    val page: Int = 1
)