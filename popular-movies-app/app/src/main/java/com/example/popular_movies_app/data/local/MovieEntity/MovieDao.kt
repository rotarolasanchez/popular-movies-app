package com.example.popular_movies_app.data.local.MovieEntity

interface MovieLocalDataSource {
    fun getAllMovies(): List<MovieRealmObject>
    fun getMovieById(id: Int): MovieRealmObject?
    fun saveMovies(movies: List<MovieRealmObject>)
}
