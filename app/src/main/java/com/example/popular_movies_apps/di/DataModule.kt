package com.example.popular_movies_apps.di


import com.example.popular_movies_apps.data.datasources.MovieListDataSourceLocal
import com.example.popular_movies_apps.data.local.MovieEntity.MovieLocalDataSource
import com.example.popular_movies_apps.data.repository.MovieRepositoryImpl
import com.example.popular_movies_apps.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        movieRepositoryImpl: MovieRepositoryImpl
    ): MovieRepository

    @Binds
    @Singleton
    abstract fun bindMovieLocalDataSource(
        impl: MovieListDataSourceLocal
    ): MovieLocalDataSource
}