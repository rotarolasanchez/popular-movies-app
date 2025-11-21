package com.example.popular_movies_apps.presentation.view.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.popular_movies_apps.domain.model.MovieDetail
import com.example.popular_movies_apps.presentation.view.atoms.BackdropImage
import com.example.popular_movies_apps.presentation.view.moleculs.MovieDetailHeader
import com.example.popular_movies_apps.presentation.view.moleculs.MovieDetailInfo

@Composable
fun MovieDetailContent(
    movie: MovieDetail,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        BackdropImage(
            imageUrl = movie.backdropUrl ?: movie.posterUrl,
            contentDescription = movie.title
        )

        MovieDetailHeader(
            title = movie.title,
            tagline = movie.tagline,
            rating = movie.voteAverage,
            releaseDate = movie.releaseDate,
            runtime = movie.runtime
        )

        Spacer(modifier = Modifier.height(8.dp))

        MovieDetailInfo(
            overview = movie.overview,
            genres = movie.genres
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}