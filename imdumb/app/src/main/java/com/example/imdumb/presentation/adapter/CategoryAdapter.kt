package com.example.imdumb.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.imdumb.databinding.ItemCategoryBinding
import com.example.imdumb.domain.model.CategoryModel
import com.example.imdumb.domain.model.MovieModel

class CategoryAdapter(
    private val categories: List<CategoryModel>,
    private val onMovieClick: (MovieModel) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.tvCategoryName.text = category.name
        
        holder.binding.rvMovies.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.binding.rvMovies.adapter = MovieAdapter(category.movieModels, onMovieClick)
    }

    override fun getItemCount() = categories.size
}
