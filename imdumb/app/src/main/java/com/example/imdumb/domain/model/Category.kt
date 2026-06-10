package com.example.imdumb.domain.model

data class Category(
    val id: Int,
    val name: String,
    val movies: List<Movie>
)
