package com.example.popular_movies_apps.presentation.state.MovieListUiState

import com.example.popular_movies_apps.domain.model.MovieDetail
import com.example.popular_movies_apps.domain.model.MovieModel

object MovieListUiState {
    data class MoviesListUiState(
        val isLoading: Boolean = false,
        val movies: List<MovieModel> = emptyList(),
        val error: String? = null
    )

    data class MovieDetailUiState(
        val isLoading: Boolean = false,
        val movie: MovieDetail? = null,
        val error: String? = null
    )
}