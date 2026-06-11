package com.example.imdumb.presentation.detail

import com.example.imdumb.domain.model.ActorModel

interface DetailContract {
    interface View {
        fun showActors(actors: List<ActorModel>)
        fun showError(message: String)
    }

    interface Presenter {
        fun attachView(view: View)
        fun detachView()
        fun loadActors(movieId: Int)
    }
}
