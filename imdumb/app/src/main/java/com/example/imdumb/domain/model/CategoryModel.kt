package com.example.imdumb.domain.model

data class CategoryModel(
    val id: Int,
    val name: String,
    val movieModels: List<MovieModel>
)
