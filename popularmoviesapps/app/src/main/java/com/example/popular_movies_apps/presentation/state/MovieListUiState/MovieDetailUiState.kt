package com.example.popular_movies_app.presentation.state.MovieListUiState

import com.example.popular_movies_app.domain.model.MovieModel

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: MovieModel? = null,
    val error: String? = null
)