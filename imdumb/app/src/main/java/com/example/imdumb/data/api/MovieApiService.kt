package com.example.imdumb.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApiService {
    // Example endpoints
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Single<Any> // Replace with DTO
}
