package com.example.popular_movies_apps.data.local.MovieEntity


interface MovieLocalDataSource {
    fun getAllMovies(): List<MovieRealmObject>
    fun getMovieById(id: Int): MovieRealmObject?
    fun saveMovies(movies: List<MovieRealmObject>)
    fun getMovieDetailById(id: Int): MovieDetailRealmObject?
    fun saveMovieDetail(detail: MovieDetailRealmObject)
}
