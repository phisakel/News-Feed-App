package com.voltek.newsfeed.base.presentation.mvi

import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

abstract class MviPresenter<ViewState : MviViewState>(
        private val initialState: ViewState
) {

    private var isFirstLaunch = true
    private var view: MviView<ViewState>? = null

    private val stateSubject = BehaviorSubject.createDefault<ViewState>(initialState)
    private val stateAndIntentsDisposables = CompositeDisposable()

    protected val state: ViewState
        get() = stateSubject.value ?: initialState

    fun attachView(view: MviView<ViewState>) {
        this.view = view

        subscribeViewForStateUpdates(view::render)
        subscribeForViewIntents(view.intents)

        if (isFirstLaunch) {
            isFirstLaunch = false
            onFirstViewAttach()
        }

        onViewAttach()
    }

    fun detachView() {
        onViewDetach()
        stateAndIntentsDisposables.clear()
        this.view = null
    }

    fun destroy() {
        onDestroy()
    }

    private fun onFirstViewAttach() {}

    private fun onViewAttach() {}

    private fun onViewDetach() {}

    private fun onDestroy() {}

    open fun handleIntent(intent: MviIntent) {}

    protected fun update(state: ViewState) {
        stateSubject.onNext(state)
    }

    private fun subscribeViewForStateUpdates(render: (ViewState) -> Unit) {
        stateAndIntentsDisposables.add(stateSubject.subscribe { render(it) })
    }

    private fun subscribeForViewIntents(intents: Observable<MviIntent>) {
        stateAndIntentsDisposables.add(intents.subscribe(::handleIntent))
    }
}
