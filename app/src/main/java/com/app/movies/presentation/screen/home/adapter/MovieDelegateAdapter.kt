package com.app.movies.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.databinding.ItemMovieBinding
import com.app.movies.presentation.screen.home.model.MovieDelegateItem
import com.app.movies.util.delegateadapter.DelegateAdapter
import com.app.movies.util.loadMovieImage

class MovieDelegateAdapter(
    val onClick: (String) -> Unit
) : DelegateAdapter<MovieDelegateItem, MovieDelegateAdapter.ViewHolder>(
    MovieDelegateItem::class.java
) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(
            ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun bindViewHolder(model: MovieDelegateItem, viewHolder: ViewHolder) = viewHolder.bind(model)

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieDelegateItem) {

            binding.apply {
                loadMovieImage(context = root.context, imageView = binding.ivImage, url = movie.image)

                tvTitle.text = movie.title

                root.setOnClickListener { onClick(movie.title) }
            }
        }
    }

}



