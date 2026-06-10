package com.example.popular_movies_app.presentation.viewmodels

import android.os.Bundle
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.popular_movies_app.domain.model.MovieModel
import com.example.popular_movies_app.domain.usecase.GetMovieDetailUseCase
import com.example.popular_movies_app.presentation.state.MovieListUiState.MovieDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val getMovieDetail: GetMovieDetailUseCase,
    //private val analytics: FirebaseAnalytics,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    var uiState by mutableStateOf(MovieDetailUiState())
        private set

    init {
        //loadMovieDetail()
    }
/*
    private fun loadMovieDetail() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            val result = getMovieDetail(movieId)
            uiState = result.fold(
                onSuccess = { movie ->
                    logViewMovieDetail(movie)
                    uiState.copy(isLoading = false, movie = movie)
                },
                onFailure = { e ->
                    uiState.copy(isLoading = false, error = e.message)
                }
            )
        }
    }*/

    private fun logViewMovieDetail(movieModel: MovieModel) {
        val bundle = Bundle().apply {
            putInt("movie_id", movieModel.id)
            putString("movie_title", movieModel.title)
        }
        //analytics.logEvent("view_movie_detail", bundle)
    }
}
