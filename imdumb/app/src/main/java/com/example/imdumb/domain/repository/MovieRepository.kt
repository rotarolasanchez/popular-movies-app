package com.example.imdumb.domain.repository

import com.example.imdumb.domain.model.Category
import io.reactivex.Single

interface MovieRepository {
    fun getMoviesByCategories(): Single<List<Category>>
}
