package gr.phisakel.newsfeed.presentation.ui.splash

import com.arellomobile.mvp.InjectViewState
import gr.phisakel.newsfeed.Logger
import gr.phisakel.newsfeed.domain.exception.NoNewsSourcesSelectedException
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import gr.phisakel.newsfeed.presentation.base.BasePresenter
import gr.phisakel.newsfeed.presentation.base.Event
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenArticlesListScreen
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import gr.phisakel.newsfeed.presentation.navigation.command.CommandSystemMessage
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class SplashPresenter(
        private val router: Router,
        private val newsSources: NewsSourcesUseCase
) : BasePresenter<SplashView>() {

    override fun event(event: Event) {}

    init {
        bind(arrayOf(newsSources))
    }

    override fun onFirstViewAttach() {
        checkNewsSources()
    }

    // TODO make new use case class for this logic
    private fun checkNewsSources() {
        // Fetch news sources in background.
        // Check, if there is no enabled news sources, open NewsSources screen with proper message.
        newsSources.execute(
                Parameter(NewsSourcesUseCase.GET),
                Consumer {
                    result(hasEnabled(it?.data ?: ArrayList()))
                },
                Consumer {
                    Logger.e(it)
                    result(false)
                },
                Action {}
        )
    }

    private fun hasEnabled(sources: List<SourceUI>): Boolean = sources.any { it.isEnabled }

    private fun result(hasEnabled: Boolean) {
        router.execute(CommandOpenArticlesListScreen())

        if (!hasEnabled) {
            router.execute(CommandOpenNewsSourcesScreen())
            router.execute(CommandSystemMessage(error = NoNewsSourcesSelectedException()))
        }
    }
}
