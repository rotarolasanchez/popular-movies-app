package com.example.imdumb.data.mappers

import com.example.imdumb.data.remote.MovieDto
import com.example.imdumb.domain.model.MovieModel

fun MovieDto.toDomain() = MovieModel(
    id = id,
    title = title,
    overview = overview,
    posterPath = if (posterPath != null) "https://image.tmdb.org/t/p/w500$posterPath" else null,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
)
