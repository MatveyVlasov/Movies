package com.app.movies.presentation.model

import com.app.movies.util.delegateadapter.DelegateAdapterItem
import com.app.movies.util.randomId

object LoadingItem : DelegateAdapterItem {

    private val id = randomId

    override fun id(): Any = id

    override fun content(): Any = this
}