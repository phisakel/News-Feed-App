package com.voltek.newsfeed.base.reactive

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface RxStreamsHolder {

    val compositeDisposable: CompositeDisposable

    fun Disposable.storeInComposite() {
        compositeDisposable.add(this)
    }

    fun resetCompositeDisposable() {
        compositeDisposable.clear()
    }
}
