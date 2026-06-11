package com.example.imdumb.data.remote

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponseDto(
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double
)

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    val genres: List<GenreDto>,
    val tagline: String?,
    val status: String,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    val popularity: Double
)

data class GenreDto(
    val id: Int,
    val name: String
)

data class CreditsResponseDto(
    val id: Int,
    val cast: List<CastDto>
)

data class CastDto(
    val id: Int,
    val name: String,
    val character: String,
    @SerializedName("profile_path") val profilePath: String?
)
