package com.example.popular_movies_apps.presentation.view.pages

import androidx.compose.runtime.Composable
import com.example.popular_movies_apps.presentation.state.MovieListUiState.MovieListUiState
import com.example.popular_movies_apps.presentation.view.atoms.ErrorMessage
import com.example.popular_movies_apps.presentation.view.atoms.LoadingIndicator
import com.example.popular_movies_apps.presentation.view.organisms.MovieDetailContent
import com.example.popular_movies_apps.presentation.view.templates.MovieDetailTemplate

@Composable
fun MovieDetailScreen(
    uiState: MovieListUiState.MovieDetailUiState,
    onBack: () -> Unit
) {
    when {
        uiState.isLoading -> LoadingIndicator()
        uiState.error != null -> ErrorMessage(message = uiState.error)
        uiState.movie != null -> {
            MovieDetailTemplate(
                title = uiState.movie.title,
                onBack = onBack
            ) {
                MovieDetailContent(movie = uiState.movie)
            }
        }
    }
}