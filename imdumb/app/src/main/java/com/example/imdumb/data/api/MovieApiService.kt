package com.example.imdumb.data.api

import com.example.imdumb.data.remote.PopularMoviesResponseDto
import io.reactivex.Single
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Single<PopularMoviesResponseDto>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<PopularMoviesResponseDto>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<PopularMoviesResponseDto>
}
