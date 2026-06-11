package com.example.imdumb.data.repository

import com.example.imdumb.data.api.MovieApiService
import com.example.imdumb.data.mappers.toDomain
import com.example.imdumb.domain.model.ActorModel
import com.example.imdumb.domain.model.CategoryModel
import com.example.imdumb.domain.repository.MovieRepository
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: MovieApiService
) : MovieRepository {

    override fun getMoviesByCategories(): Single<List<CategoryModel>> {
        return Single.zip(
            apiService.getPopularMovies().map { response ->
                CategoryModel(1, "Populares", response.results.map { it.toDomain() })
            },
            apiService.getTopRatedMovies().map { response ->
                CategoryModel(2, "Mejor Valoradas", response.results.map { it.toDomain() })
            },
            apiService.getUpcomingMovies().map { response ->
                CategoryModel(3, "Próximamente", response.results.map { it.toDomain() })
            },
            Function3 { cat1, cat2, cat3 ->
                listOf(cat1, cat2, cat3)
            }
        )
    }

    override fun getMovieCredits(movieId: Int): Single<List<ActorModel>> {
        return apiService.getMovieCredits(movieId).map { response ->
            response.cast
                .filter { it.profilePath != null } // Filtramos solo los que tienen imagen
                .take(15) // Tomamos los principales
                .map { it.toDomain() }
        }
    }
}
