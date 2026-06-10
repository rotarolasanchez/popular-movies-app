package com.example.imdumb.data.repository

import com.example.imdumb.data.api.MovieApiService
import com.example.imdumb.data.mappers.toDomain
import com.example.imdumb.domain.model.Category
import com.example.imdumb.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override fun getMoviesByCategories(): Single<List<Category>> {
        return Single.zip(
            apiService.getPopularMovies().map { response ->
                Category(1, "Populares", response.results.map { it.toDomain() })
            },
            apiService.getTopRatedMovies().map { response ->
                Category(2, "Mejor Valoradas", response.results.map { it.toDomain() })
            },
            apiService.getUpcomingMovies().map { response ->
                Category(3, "Próximamente", response.results.map { it.toDomain() })
            },
            Function3 { cat1, cat2, cat3 ->
                listOf(cat1, cat2, cat3)
            }
        )
    }
}
