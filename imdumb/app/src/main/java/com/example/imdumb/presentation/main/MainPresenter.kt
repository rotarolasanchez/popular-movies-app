package com.example.imdumb.presentation.main

import com.example.imdumb.domain.usecase.GetMoviesByCategoriesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val getMoviesByCategoriesUseCase: GetMoviesByCategoriesUseCase
) : MainContract.Presenter {

    private var view: MainContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: MainContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        disposables.clear()
    }

    override fun loadMovies() {
        view?.showLoading()
        disposables.add(
            getMoviesByCategoriesUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ categories ->
                    view?.hideLoading()
                    view?.showMovies(categories)
                }, { error ->
                    view?.hideLoading()
                    view?.showError(error.message ?: "Unknown error")
                })
        )
    }
}
