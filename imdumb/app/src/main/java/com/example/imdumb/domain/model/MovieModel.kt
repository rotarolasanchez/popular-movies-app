package com.example.imdumb.domain.model

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterPath: String,
    val voteAverage: Double,
    val releaseDate: String
)
