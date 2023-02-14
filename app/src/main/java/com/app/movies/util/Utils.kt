package com.app.movies.util

import android.content.Context
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.app.movies.R
import com.bumptech.glide.Glide
import java.util.*

val randomId: String
    get() = UUID.randomUUID().toString()

fun String.toImageUrl() = "https://image.tmdb.org/t/p/original$this"

fun loadImage(
    context: Context,
    imageView: ImageView,
    url: String,
    @DrawableRes defaultImage: Int,
    isClip: Boolean = true
) {
    val image = url.ifBlank { defaultImage }

    Glide.with(context)
        .load(image)
        .into(imageView)

    imageView.clipToOutline = isClip
}

fun loadMovieImage(context: Context, imageView: ImageView, url: String) =
    loadImage(context = context, imageView = imageView, url = url, defaultImage = R.drawable.ic_movie)