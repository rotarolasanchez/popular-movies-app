package com.example.imdumb.domain.usecase

import com.example.imdumb.domain.model.ActorModel
import com.example.imdumb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    fun execute(movieId: Int): Single<List<ActorModel>> {
        return repository.getMovieCredits(movieId)
    }
}
