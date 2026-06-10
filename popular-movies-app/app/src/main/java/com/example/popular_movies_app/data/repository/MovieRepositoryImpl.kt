package com.example.popular_movies_app.data.repository

import com.example.popular_movies_app.core.network.ApiService
import com.example.popular_movies_app.data.local.MovieEntity.MovieLocalDataSource
import com.example.popular_movies_app.data.mappers.toDomain
import com.example.popular_movies_app.data.mappers.toRealm
import com.example.popular_movies_app.domain.model.MovieModel
import com.example.popular_movies_app.domain.repository.MovieRepository
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

    /*override suspend fun getMovieDetail(movieId: Int): Result<MovieModel> {
        return try {
            // 1. Intentar local
            val localMovie = local.getMovieById(movieId)
            if (localMovie != null) {
                return Result.success(localMovie.toDomain())
            }

            // 2. Si no está, llamar API
            val remoteMovie = remote.getMovieDetail(movieId)
            val realm = remoteMovie.toRealm()
            local.saveMovies(listOf(realm))

            Result.success(realm.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }*/
}
