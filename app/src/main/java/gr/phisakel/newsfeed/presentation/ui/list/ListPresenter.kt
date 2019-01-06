package gr.phisakel.newsfeed.presentation.ui.list

import com.arellomobile.mvp.InjectViewState
import gr.phisakel.newsfeed.Logger
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.articles.GetArticlesUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import gr.phisakel.newsfeed.presentation.base.BasePresenter
import gr.phisakel.newsfeed.presentation.base.Event
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenArticleDetailsScreen
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

@InjectViewState
class ListPresenter(
        private val router: Router,
        private val articles: GetArticlesUseCase,
        private val newsSourcesChanges: NewsSourcesUpdatesUseCase
) : BasePresenter<ListView>() {

    // Holds current model through full presenter lifecycle
    private val model: ListModel = ListModel { viewState.render(it as ListModel) }

    init {
        bind(arrayOf(articles, newsSourcesChanges))
    }

    override fun onFirstViewAttach() {
        listenForChanges()
        loadArticles()
    }

    // View event presenter about events using this method
    override fun event(event: Event) {
        when (event) {
            is Event.OpenArticleDetails -> router.execute(CommandOpenArticleDetailsScreen(event.article))
            is Event.OpenNewsSources -> router.execute(CommandOpenNewsSourcesScreen())
            is Event.Refresh -> {
                if (!model.loading) {
                    loadArticles()
                }
            }
        }
    }

    override fun attachView(view: ListView?) {
        super.attachView(view)
        model.scrollToTop = false
    }

    private fun loadArticles() {
        with(model) {
            articles.clear()
            loading = true
            message = ""
            update()
        }

        articles.execute(
                Parameter(),
                Consumer {
                    with(model) {
                        addData(it.data)
                        message = it.message
                        update()
                    }
                },
                Consumer {
                    model.message = it.message ?: ""
                    finishLoading()
                },
                Action {
                    finishLoading()
                }
        )
    }

    private fun listenForChanges() {
        // Listen for enabled news sources changes and reload articles when it happens.
        newsSourcesChanges.execute(
                Parameter(),
                Consumer {
                    model.scrollToTop = true
                    loadArticles()
                },
                Consumer {
                    it.printStackTrace()
                },
                Action {}
        )
    }

    private fun finishLoading() {
        model.loading = false
        model.update()
    }
}
