package com.voltek.newsfeed.features.newssource.presentation

import com.voltek.newsfeed.base.presentation.mvi.MviPresenter
import com.voltek.newsfeed.base.reactive.RxSchedulers

class NewsSourcePresenter(
        private val rxSchedulers: RxSchedulers
) : MviPresenter<NewsSourceState>(NewsSourceState())
