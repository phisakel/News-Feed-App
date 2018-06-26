package com.voltek.newsfeed.base.reactive

import io.reactivex.Scheduler
import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

interface RxSchedulers {
    val ui: Scheduler
    val io: Scheduler
    val computation: Scheduler

    fun <T> observableIo() = ObservableTransformer<T, T> { Observable ->
        Observable.subscribeOn(io).observeOn(ui)
    }

    fun <T> singleIo() = SingleTransformer<T, T> { Single ->
        Single.subscribeOn(io).observeOn(ui)
    }

    fun completableIo() = CompletableTransformer { Completable ->
        Completable.subscribeOn(io).observeOn(ui)
    }

    fun <T> observableComputation() = ObservableTransformer<T, T> { Observable ->
        Observable.subscribeOn(computation).observeOn(ui)
    }

    fun <T> singleComputation() = SingleTransformer<T, T> { Single ->
        Single.subscribeOn(computation).observeOn(ui)
    }

    fun completableComputation() = CompletableTransformer { Completable ->
        Completable.subscribeOn(computation).observeOn(ui)
    }
}
