package com.example.imdumb.presentation.detail

import com.example.imdumb.domain.usecase.GetMovieCreditsUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val getMovieCreditsUseCase: GetMovieCreditsUseCase
) : DetailContract.Presenter {

    private var view: DetailContract.View? = null
    private val disposables = CompositeDisposable()

    override fun attachView(view: DetailContract.View) {
        this.view = view
    }

    override fun detachView() {
        this.view = null
        disposables.clear()
    }

    override fun loadActors(movieId: Int) {
        disposables.add(
            getMovieCreditsUseCase.execute(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ actors ->
                    view?.showActors(actors)
                }, { error ->
                    view?.showError(error.message ?: "Error loading actors")
                })
        )
    }
}
