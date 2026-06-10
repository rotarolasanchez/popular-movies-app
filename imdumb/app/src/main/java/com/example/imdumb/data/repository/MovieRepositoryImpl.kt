package com.example.imdumb.data.repository

import com.example.imdumb.data.api.MovieApiService
import com.example.imdumb.domain.model.Category
import com.example.imdumb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {
    override fun getMoviesByCategories(): Single<List<Category>> {
        // Implementation that calls API and maps to domain model
        return Single.just(emptyList()) 
    }
}
