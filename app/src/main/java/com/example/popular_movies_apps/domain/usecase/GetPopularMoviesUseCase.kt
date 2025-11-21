package com.example.popular_movies_apps.domain.usecase

import com.example.popular_movies_apps.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    suspend operator fun invoke(forceRefresh: Boolean = false) =
        repository.getPopularMovies(forceRefresh)
}
