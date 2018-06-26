package com.voltek.newsfeed.features.entrypoint.domain

import io.reactivex.Single

class EntryPointInteractor {

    fun hasEnabledNewsSources(): Single<Boolean> = Single.just(true)
}
