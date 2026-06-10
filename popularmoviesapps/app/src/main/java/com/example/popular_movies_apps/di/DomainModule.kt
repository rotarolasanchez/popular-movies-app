package com.example.popular_movies_app.di

import com.example.popular_movies_app.domain.repository.MovieRepository
import com.example.popular_movies_app.domain.usecase.GetPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideGetPopularMoviesUseCase(
        repository: MovieRepository
    ): GetPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
}