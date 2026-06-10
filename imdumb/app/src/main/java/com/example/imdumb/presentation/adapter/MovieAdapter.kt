package com.example.imdumb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imdumb.databinding.ItemMovieBinding
import com.example.imdumb.domain.model.MovieModel

class MovieAdapter(
    private val movieModels: List<MovieModel>,
    private val onMovieClick: (MovieModel) -> Unit
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieModels[position]
        holder.binding.tvTitle.text = movie.title
        Glide.with(holder.itemView.context)
            .load(movie.posterPath)
            .centerCrop()
            .placeholder(android.R.drawable.progress_indeterminate_horizontal)
            .error(android.R.drawable.stat_notify_error)
            .into(holder.binding.ivPoster)

        holder.itemView.setOnClickListener { onMovieClick(movie) }
    }

    override fun getItemCount() = movieModels.size
}
