package com.app.movies.util

import androidx.annotation.StringRes

interface ResourceProvider {
    fun string(@StringRes resId: Int): String
    fun string(@StringRes resId: Int, vararg formatArgs: Any): String
    fun string(resId: Int, quantity: Int): String
}