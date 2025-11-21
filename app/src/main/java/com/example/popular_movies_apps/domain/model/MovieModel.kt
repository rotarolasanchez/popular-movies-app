package com.example.popular_movies_apps.domain.model


data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double
)

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String?,
    val voteAverage: Double,
    val genres: List<String>,
    val runtime: Int?,
    val tagline: String?
)
