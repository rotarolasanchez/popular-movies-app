package com.example.popular_movies_app.domain.usecase

import com.example.popular_movies_app.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    //suspend operator fun invoke(movieId: Int) = repository.getMovieDetail(movieId)
}