package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.domain.usecase.articles.GetArticlesUseCase
import com.voltek.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import com.voltek.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import com.voltek.newsfeed.presentation.navigation.proxy.RouterOld
import com.voltek.newsfeed.presentation.ui.details.DetailsPresenter
import com.voltek.newsfeed.presentation.ui.list.ListPresenter
import com.voltek.newsfeed.presentation.ui.newssources.NewsSourcesPresenter
import com.voltek.newsfeed.presentation.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule {

    @Provides
    fun provideSplashPresenter(router: RouterOld, newsSourcesUseCase: NewsSourcesUseCase)
            = SplashPresenter(router, newsSourcesUseCase)

    @Provides
    fun provideNewsSourcesPresenter(
            newsSourcesUseCase: NewsSourcesUseCase,
            enableNewsSourceUseCase: EnableNewsSourceUseCase
    ) = NewsSourcesPresenter(newsSourcesUseCase, enableNewsSourceUseCase)

    @Provides
    fun provideListPresenter(router: RouterOld,
                             getArticlesUseCase: GetArticlesUseCase,
                             newsSourcesUpdatesUseCase: NewsSourcesUpdatesUseCase
    ) = ListPresenter(router, getArticlesUseCase, newsSourcesUpdatesUseCase)

    @Provides
    fun provideDetailsPresenter(router: RouterOld) = DetailsPresenter(router)
}
