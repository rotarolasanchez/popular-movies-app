package com.example.imdumb.data.mappers

import com.example.imdumb.data.remote.CastDto
import com.example.imdumb.data.remote.MovieDto
import com.example.imdumb.domain.model.ActorModel
import com.example.imdumb.domain.model.MovieModel

fun MovieDto.toDomain() = MovieModel(
    id = id,
    title = title,
    overview = overview,
    posterPath = if (posterPath != null) "https://image.tmdb.org/t/p/w500$posterPath" else null,
    backdropPath = if (backdropPath != null) "https://image.tmdb.org/t/p/w780$backdropPath" else null,
    releaseDate = releaseDate,
    voteAverage = voteAverage
)

fun CastDto.toDomain() = ActorModel(
    id = id,
    name = name,
    character = character,
    profilePath = if (profilePath != null) "https://image.tmdb.org/t/p/w185$profilePath" else null
)
