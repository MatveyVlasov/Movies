package com.app.movies.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext

class ResourceProviderImpl(
    @ApplicationContext private val appContext: Context
): ResourceProvider {
    override fun string(resId: Int): String = appContext.getString(resId)

    override fun string(resId: Int, vararg formatArgs: Any): String = appContext.getString(resId, *formatArgs)

    override fun string(resId: Int, quantity: Int): String = appContext.resources.getQuantityString(resId, quantity, quantity)
}