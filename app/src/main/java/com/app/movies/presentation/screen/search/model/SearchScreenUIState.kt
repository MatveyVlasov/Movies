package com.app.movies.presentation.screen.search.model

import com.app.movies.util.delegateadapter.DelegateAdapterItem

data class SearchScreenUIState(
    val movies: List<DelegateAdapterItem> = emptyList(),
    val searchQuery: String = ""
)