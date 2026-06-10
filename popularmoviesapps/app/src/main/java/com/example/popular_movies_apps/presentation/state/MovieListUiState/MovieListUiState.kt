package com.example.popular_movies_app.presentation.state.MovieListUiState

import com.example.popular_movies_app.domain.model.MovieModel

data class MoviesListUiState(
    val isLoading: Boolean = false,
    val movies: List<MovieModel> = emptyList(),
    val error: String? = null
)