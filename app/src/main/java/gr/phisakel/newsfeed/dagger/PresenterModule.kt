package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.analytics.Analytics
import gr.phisakel.newsfeed.domain.usecase.articles.GetArticlesUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router
import gr.phisakel.newsfeed.presentation.ui.details.DetailsPresenter
import gr.phisakel.newsfeed.presentation.ui.list.ListPresenter
import gr.phisakel.newsfeed.presentation.ui.newssources.NewsSourcesPresenter
import gr.phisakel.newsfeed.presentation.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideSplashPresenter(
            router: Router,
            newsSourcesUseCase: NewsSourcesUseCase
    ) = SplashPresenter(router, newsSourcesUseCase)

    @Provides
    fun provideNewsSourcesPresenter(
            newsSourcesUseCase: NewsSourcesUseCase,
            enableNewsSourceUseCase: EnableNewsSourceUseCase,
            analytics: Analytics
    ) = NewsSourcesPresenter(newsSourcesUseCase, enableNewsSourceUseCase, analytics)

    @Provides
    fun provideListPresenter(
            router: Router,
            getArticlesUseCase: GetArticlesUseCase,
            newsSourcesUpdatesUseCase: NewsSourcesUpdatesUseCase
    ) = ListPresenter(router, getArticlesUseCase, newsSourcesUpdatesUseCase)

    @Provides
    fun provideDetailsPresenter(
            router: Router,
            analytics: Analytics
    ) = DetailsPresenter(router, analytics)
}
