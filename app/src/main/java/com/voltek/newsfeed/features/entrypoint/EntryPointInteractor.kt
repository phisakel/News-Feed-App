package com.voltek.newsfeed.features.entrypoint

import io.reactivex.Single

interface EntryPointInteractor {

    fun hasEnabledNewsSources(): Single<Boolean>
}
