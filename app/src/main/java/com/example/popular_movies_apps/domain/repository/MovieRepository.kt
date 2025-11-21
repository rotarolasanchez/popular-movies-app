package com.example.popular_movies_apps.domain.repository

import com.example.popular_movies_apps.domain.model.MovieModel

interface MovieRepository {
    suspend fun getPopularMovies(forceRefresh: Boolean = false): Result<List<MovieModel>>
    //suspend fun getMovieDetail(movieId: Int): Result<MovieModel>
}
