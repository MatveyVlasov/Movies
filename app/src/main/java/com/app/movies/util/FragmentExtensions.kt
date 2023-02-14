package com.app.movies.util

import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.app.movies.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

fun Fragment.setupBottomNavigation(isVisible: Boolean) {
    requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView).isVisible = isVisible
}

fun Fragment.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_LONG
) {
    Snackbar.make(requireView(), message, duration).show()
}

fun Fragment.showSnackbar(
    @StringRes message: Int,
    duration: Int = Snackbar.LENGTH_LONG
) = showSnackbar(
    message = getString(message),
    duration = duration
)