package com.example.popular_movies_app.domain.repository

import com.example.popular_movies_app.domain.model.MovieModel

interface MovieRepository {
    suspend fun getPopularMovies(forceRefresh: Boolean = false): Result<List<MovieModel>>
    //suspend fun getMovieDetail(movieId: Int): Result<MovieModel>
}
