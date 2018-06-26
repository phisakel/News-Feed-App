package com.voltek.newsfeed.features.entrypoint

import com.voltek.newsfeed.Logger
import com.voltek.newsfeed.base.navigation.Router
import com.voltek.newsfeed.base.navigation.commands.Go
import com.voltek.newsfeed.base.navigation.commands.Replace
import com.voltek.newsfeed.base.presentation.RxSchedulers
import com.voltek.newsfeed.base.presentation.RxStreamsHolder
import com.voltek.newsfeed.common.navigation.Screen
import io.reactivex.disposables.CompositeDisposable

class EntryPointWizard(
        private val entryPointInteractor: EntryPointInteractor,
        private val router: Router,
        private val rxSchedulers: RxSchedulers
) : RxStreamsHolder {

    override val compositeDisposable = CompositeDisposable()

    private var hasEnabledNewsSources = false

    fun start() {
        nextStep()
    }

    private fun nextStep() = when {
        !hasEnabledNewsSources -> checkEnabledNewsSources()
        else -> finish()
    }

    private fun checkEnabledNewsSources() {
        entryPointInteractor.hasEnabledNewsSources()
                .compose(rxSchedulers.singleIo())
                .subscribe({
                    if (it) {
                        hasEnabledNewsSources = true
                        nextStep()
                    } else {
                        router.execute(Replace(Screen.NewsSources))
                    }
                }, {
                    Logger.e(it)
                })
                .storeInComposite()
    }

    private fun finish() {
        resetCompositeDisposable()
        router.execute(Go(Screen.ArticlesFeed))
    }
}
