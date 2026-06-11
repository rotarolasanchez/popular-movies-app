package com.example.imdumb.domain.usecase

import com.example.imdumb.domain.model.CategoryModel
import com.example.imdumb.domain.repository.MovieRepository
import io.reactivex.Single
import javax.inject.Inject

class GetMoviesByCategoriesUseCase @Inject constructor(
    private val repository: MovieRepository
) {
    fun execute(): Single<List<CategoryModel>> {
        return repository.getMoviesByCategories()
    }
}
