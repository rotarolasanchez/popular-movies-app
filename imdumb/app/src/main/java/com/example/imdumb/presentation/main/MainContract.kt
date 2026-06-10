package com.example.imdumb.presentation.main

interface MainContract {
    interface View {
        fun showLoading()
        fun hideLoading()
        fun showMovies(categories: List<Any>) // Replace Any with Domain model
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadMovies()
    }
}
