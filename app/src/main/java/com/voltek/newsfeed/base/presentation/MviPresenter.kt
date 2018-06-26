package com.voltek.newsfeed.base.presentation

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

    open fun onFirstViewAttach() {}

    open fun onViewAttach() {}

    open fun onViewDetach() {}

    open fun onDestroy() {}

    open fun handleIntent(intent: MviIntent) {}

    private fun subscribeViewForStateUpdates(render: (ViewState) -> Unit) {
        stateAndIntentsDisposables.add(stateSubject.subscribe { render(it) })
    }

    protected fun subscribeForViewIntents(intents: Observable<MviIntent>) {
        stateAndIntentsDisposables.add(intents.subscribe(::handleIntent))
    }
}
