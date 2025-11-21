package com.example.popular_movies_apps.presentation.view.atoms

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.popular_movies_apps.presentation.view.pages.MovieDetailScreen
import com.example.popular_movies_apps.presentation.view.pages.MoviesListScreen
import com.example.popular_movies_apps.presentation.viewmodels.MovieDetailViewModel
import com.example.popular_movies_apps.presentation.viewmodels.MoviesListViewModel


object Routes {
    const val LIST = "list"
    const val DETAIL = "detail/{movieId}"
}

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.LIST) {
        composable(Routes.LIST) {
            val vm: MoviesListViewModel = hiltViewModel()
            MoviesListScreen(
                uiState = vm.uiState,
                onMovieClick = { id -> navController.navigate("detail/$id") },
                onRefresh = { vm.loadMovies(true) }
            )
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            val vm: MovieDetailViewModel = hiltViewModel()
            MovieDetailScreen(uiState = vm.uiState, onBack = { navController.popBackStack() })
        }
    }
}