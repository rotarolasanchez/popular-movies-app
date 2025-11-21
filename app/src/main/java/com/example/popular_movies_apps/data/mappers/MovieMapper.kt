package com.example.popular_movies_apps.data.mappers

import com.example.popular_movies_apps.data.remote.MovieDetailDto
import com.example.popular_movies_apps.data.remote.MovieDto
import com.example.popular_movies_apps.domain.model.MovieDetail
import com.example.popular_movies_apps.domain.model.MovieModel
import com.example.popular_movies_apps.data.local.MovieEntity.MovieDetailRealmObject
import com.example.popular_movies_apps.data.local.MovieEntity.MovieRealmObject

fun MovieDto.toRealm() = MovieRealmObject().apply {
    id = this@toRealm.id
    title = this@toRealm.title
    overview = this@toRealm.overview
    posterPath = this@toRealm.posterPath
    releaseDate = this@toRealm.releaseDate
    voteAverage = this@toRealm.voteAverage
}

fun MovieRealmObject.toDomain(): MovieModel = MovieModel(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage
)



fun MovieDetailRealmObject.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
    backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = emptyList(), // No guardado actualmente
    runtime = runtime,
    tagline = tagline
)

fun MovieDetailDto.toDomain() = MovieDetail(
    id = id,
    title = title,
    overview = overview,
    posterUrl = posterPath?.let { "https://image.tmdb.org/t/p/w500$it" },
    backdropUrl = backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" },
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    genres = genres.map { it.name },
    runtime = runtime,
    tagline = tagline
)

fun MovieDetailDto.toRealm() = MovieDetailRealmObject().apply {
    id = this@toRealm.id
    title = this@toRealm.title
    overview = this@toRealm.overview
    posterPath = this@toRealm.posterPath
    backdropPath = this@toRealm.backdropPath
    releaseDate = this@toRealm.releaseDate
    voteAverage = this@toRealm.voteAverage
    runtime = this@toRealm.runtime
    tagline = this@toRealm.tagline
}


