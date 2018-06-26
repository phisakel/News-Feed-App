package com.voltek.newsfeed.base.presentation.mvi

import com.voltek.newsfeed.base.presentation.BaseFragment
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

abstract class MviFragment<ViewState: MviViewState> : BaseFragment(), MviView<ViewState> {

    private val intentsSubject = PublishSubject.create<MviIntent>()
    override val intents: Observable<MviIntent> = intentsSubject.hide()

    override fun onResume() {
        super.onResume()
        getPresenter().attachView(this)
    }

    override fun onPause() {
        getPresenter().detachView()
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()

        if (activity?.isFinishing != false) {
            getPresenter().destroy()
            return
        }

        if (isStateSaved) {
            return
        }

        var anyParentIsRemoving = false
        var parent = parentFragment
        while (!anyParentIsRemoving && parent != null) {
            anyParentIsRemoving = parent.isRemoving
            parent = parent.parentFragment
        }

        if (isRemoving || anyParentIsRemoving) {
            getPresenter().destroy()
        }
    }

    abstract fun getPresenter(): MviPresenter<ViewState>

    protected fun intent(intent: MviIntent) {
        intentsSubject.onNext(intent)
    }
}
