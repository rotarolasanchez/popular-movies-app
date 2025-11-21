package com.example.popular_movies_apps.core.network

import com.example.popular_movies_apps.data.remote.MovieDetailDto
import com.example.popular_movies_apps.data.remote.PopularMoviesResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key: String = "622dd8f402097c0db5eb4fb12dbd7513"
    ): PopularMoviesResponseDto

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") movieId: Int
    ): MovieDetailDto
}