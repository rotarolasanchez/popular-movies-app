package com.example.popular_movies_apps.data.repository

import com.example.popular_movies_apps.core.network.ApiService
import com.example.popular_movies_apps.data.local.MovieEntity.MovieLocalDataSource
import com.example.popular_movies_apps.data.mappers.toDomain
import com.example.popular_movies_apps.data.mappers.toRealm
import com.example.popular_movies_apps.domain.model.MovieDetail
import com.example.popular_movies_apps.domain.model.MovieModel
import com.example.popular_movies_apps.domain.repository.MovieRepository
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val remote: ApiService,
    private val local: MovieLocalDataSource
) : MovieRepository {

    override suspend fun getPopularMovies(forceRefresh: Boolean): Result<List<MovieModel>> {
        return try {
            val localMovies = local.getAllMovies()

            val shouldFetchFromApi = forceRefresh || localMovies.isEmpty()

            if (shouldFetchFromApi) {
                val response = remote.getPopularMovies()
                val realmObjects = response.results.map { it.toRealm() }
                local.saveMovies(realmObjects)
            }

            val finalMovies = local.getAllMovies().map { it.toDomain() }
            Result.success(finalMovies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetail> = runCatching {
        local.getMovieDetailById(movieId)?.toDomain()
            ?: remote.getMovieDetail(movieId).let { dto ->
                val realmObj = dto.toRealm()
                local.saveMovieDetail(realmObj)
                dto.toDomain()
            }
    }
}
