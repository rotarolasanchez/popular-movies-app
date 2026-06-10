package com.example.popular_movies_app.domain.usecase

import com.example.popular_movies_app.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false) =
        repository.getPopularMovies(forceRefresh)
}
