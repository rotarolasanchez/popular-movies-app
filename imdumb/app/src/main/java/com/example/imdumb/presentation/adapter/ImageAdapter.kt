package com.example.imdumb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdumb.databinding.ItemImageBinding

class ImageAdapter(private val images: List<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .centerCrop()
            .into(holder.binding.imageView)

    }

    override fun getItemCount() = images.size
}
