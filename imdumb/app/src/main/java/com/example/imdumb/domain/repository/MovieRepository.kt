package com.example.imdumb.domain.repository

import com.example.imdumb.domain.model.ActorModel
import com.example.imdumb.domain.model.CategoryModel
import io.reactivex.Single

interface MovieRepository {
    fun getMoviesByCategories(): Single<List<CategoryModel>>
    fun getMovieCredits(movieId: Int): Single<List<ActorModel>>
}
