package com.voltek.newsfeed.base.presentation

import android.support.annotation.CallSuper
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    private val compositeDisposable = CompositeDisposable()

    open fun handleIntent(intent: MviIntent) {}

    @CallSuper
    override fun onDestroy() {
        compositeDisposable.clear()
    }

    protected fun Disposable.storeInComposite() {
        compositeDisposable.add(this)
    }
}
