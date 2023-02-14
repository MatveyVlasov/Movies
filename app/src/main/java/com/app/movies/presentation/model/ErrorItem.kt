package com.app.movies.presentation.model

import com.app.movies.util.delegateadapter.DelegateAdapterItem
import com.app.movies.util.randomId

data class ErrorItem(
    val message: String
) : DelegateAdapterItem {

    private val id = randomId

    override fun id(): Any = id

    override fun content(): Any = this
}