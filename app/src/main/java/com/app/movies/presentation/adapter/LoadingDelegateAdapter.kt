package com.app.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.movies.databinding.ItemLoadingBinding
import com.app.movies.presentation.model.LoadingItem
import com.app.movies.util.delegateadapter.DelegateAdapter

class LoadingDelegateAdapter :
    DelegateAdapter<LoadingItem, LoadingDelegateAdapter.ViewHolder>(LoadingItem::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun bindViewHolder(model: LoadingItem, viewHolder: ViewHolder) = Unit

    inner class ViewHolder(binding: ItemLoadingBinding) : RecyclerView.ViewHolder(binding.root)

}