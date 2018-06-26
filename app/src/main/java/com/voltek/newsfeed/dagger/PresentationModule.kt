package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.base.reactive.RxSchedulers
import com.voltek.newsfeed.features.newssource.presentation.NewsSourcePresenter
import dagger.Module
import dagger.Provides

@Module
class PresentationModule {

    @Provides
    fun provideNewsSourcePresenter(rxSchedulers: RxSchedulers) = NewsSourcePresenter(rxSchedulers)
}
