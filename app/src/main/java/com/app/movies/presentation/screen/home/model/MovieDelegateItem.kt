package com.app.movies.presentation.screen.home.model

import com.app.movies.util.delegateadapter.DelegateAdapterItem

data class MovieDelegateItem(
    val id: Int,
    val title: String,
    val image: String
) : DelegateAdapterItem {

    override fun id() = id

    override fun content(): Any = this
}


