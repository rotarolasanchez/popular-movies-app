package com.example.popular_movies_apps.presentation.view.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.popular_movies_apps.domain.model.MovieModel
import com.example.popular_movies_apps.presentation.state.MovieListUiState.MovieListUiState
import com.example.popular_movies_apps.presentation.view.organisms.MoviesGrid
import com.example.popular_movies_apps.presentation.view.atoms.ErrorMessage
import com.example.popular_movies_apps.presentation.view.atoms.LoadingIndicator
import com.example.popular_movies_apps.presentation.view.templates.MoviesListTemplate
import com.example.popular_movies_apps.presentation.viewmodels.MoviesListViewModel

@Composable
fun MoviesListScreen(
    uiState: com.example.popular_movies_apps.presentation.state.MovieListUiState.MovieListUiState.MoviesListUiState,
    onMovieClick: (Int) -> Unit,
    onRefresh: () -> Unit
) {
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
