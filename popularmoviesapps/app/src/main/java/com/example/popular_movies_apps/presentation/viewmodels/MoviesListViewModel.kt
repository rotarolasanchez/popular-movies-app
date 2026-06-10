package com.example.popular_movies_app.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popular_movies_app.domain.usecase.GetPopularMoviesUseCase
import com.example.popular_movies_app.presentation.state.MovieListUiState.MoviesListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase
) : ViewModel() {

    var uiState by mutableStateOf(MoviesListUiState())
        private set

    init {
        loadMovies(forceRefresh = false)
    }

    fun loadMovies(forceRefresh: Boolean) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = getPopularMoviesUseCase(forceRefresh)
            uiState = result.fold(
                onSuccess = { movies ->
                    uiState.copy(isLoading = false, movies = movies)
                },
                onFailure = { e ->
                    uiState.copy(isLoading = false, error = e.message ?: "Error desconocido")
                }
            )
        }
    }

}
