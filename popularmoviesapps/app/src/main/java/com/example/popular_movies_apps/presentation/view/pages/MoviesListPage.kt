package com.example.popular_movies_app.presentation.view.pages

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.popular_movies_app.presentation.view.atoms.ErrorMessage
import com.example.popular_movies_app.presentation.view.atoms.LoadingIndicator
import com.example.popular_movies_app.presentation.view.organisms.MoviesGrid
import com.example.popular_movies_app.presentation.view.templates.MoviesListTemplate
import com.example.popular_movies_app.presentation.viewmodels.MoviesListViewModel

@Composable
fun MoviesListScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MoviesListViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    MoviesListTemplate(title = "Películas Populares") {
        when {
            uiState.isLoading -> LoadingIndicator()
            uiState.error != null -> ErrorMessage(message = uiState.error)
            else -> MoviesGrid(
                movies = uiState.movies,
                onMovieClick = onMovieClick
            )
        }
    }
}