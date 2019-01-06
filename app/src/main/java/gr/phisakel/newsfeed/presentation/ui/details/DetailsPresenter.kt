package gr.phisakel.newsfeed.presentation.ui.details

import com.arellomobile.mvp.InjectViewState
import gr.phisakel.newsfeed.analytics.Analytics
import gr.phisakel.newsfeed.presentation.base.BasePresenter
import gr.phisakel.newsfeed.presentation.base.Event
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.navigation.command.CommandBack
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenNewsSourcesScreen
import gr.phisakel.newsfeed.presentation.navigation.command.CommandOpenWebsite
import gr.phisakel.newsfeed.presentation.navigation.command.CommandShareArticle
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router

@InjectViewState
class DetailsPresenter(
        private val router: Router,
        private val analytics: Analytics
) : BasePresenter<DetailsView>() {

    private val model: DetailsModel = DetailsModel { viewState.render(it as DetailsModel) }

    private lateinit var article: ArticleUI

    override fun event(event: Event) {
        when (event) {
            is Event.Share -> {
                if (!article.isEmpty()) {
                    analytics.articleShare(article.title ?: "", article.url ?: "", article.source)
                    router.execute(CommandShareArticle(article.title ?: "", article.url ?: ""))
                }
            }
            is Event.OpenWebsite -> router.execute(CommandOpenWebsite(article.url ?: ""))
            is Event.OpenNewsSources -> router.execute(CommandOpenNewsSourcesScreen())
            is Event.Back -> router.execute(CommandBack())
        }
    }

    fun setArticle(articleUI: ArticleUI) {
        if (!this::article.isInitialized && !articleUI.isEmpty()) {
            analytics.articleView(articleUI.title ?: "", articleUI.url ?: "", articleUI.source)
        }

        article = articleUI

        if (article.isEmpty()) {
            with(model) {
                articleLoaded = false
                update()
            }
        } else {
            with(model) {
                articleLoaded = true
                description = article.description ?: ""
                title = article.title ?: ""
                urlToImage = article.urlToImage ?: ""
                source = article.source
                update()
            }
        }
    }
}
