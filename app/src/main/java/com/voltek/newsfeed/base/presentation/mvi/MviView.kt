package com.voltek.newsfeed.base.presentation.mvi

import com.arellomobile.mvp.MvpView
import io.reactivex.Observable

interface MviView<ViewState : MviViewState> : MvpView {

    val intents: Observable<MviIntent>

    fun render(state: ViewState)
}
