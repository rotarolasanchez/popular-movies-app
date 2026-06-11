package com.example.imdumb.presentation.main

import com.example.imdumb.domain.model.CategoryModel

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMovies(categories: List<CategoryModel>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadMovies()
    }
}
