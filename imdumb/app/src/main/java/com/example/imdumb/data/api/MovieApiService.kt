package com.example.imdumb.data.api

import com.example.imdumb.data.remote.CreditsResponseDto
import com.example.imdumb.data.remote.PopularMoviesResponseDto
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(): Single<PopularMoviesResponseDto>

    @GET("movie/top_rated")
    fun getTopRatedMovies(): Single<PopularMoviesResponseDto>

    @GET("movie/upcoming")
    fun getUpcomingMovies(): Single<PopularMoviesResponseDto>

    @GET("movie/{movie_id}/credits")
    fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): Single<CreditsResponseDto>
}
