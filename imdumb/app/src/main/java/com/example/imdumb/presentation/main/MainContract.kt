package com.example.imdumb.presentation.main

import com.example.imdumb.domain.model.Category

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMovies(categories: List<Category>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadMovies()
    }
}
