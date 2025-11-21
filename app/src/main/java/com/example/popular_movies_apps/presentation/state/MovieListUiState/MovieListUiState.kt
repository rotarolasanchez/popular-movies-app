package com.example.popular_movies_apps.presentation.state.MovieListUiState

import com.example.popular_movies_apps.domain.model.MovieModel

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val movies: List<MovieModel> = emptyList(),
    val error: String? = null
)