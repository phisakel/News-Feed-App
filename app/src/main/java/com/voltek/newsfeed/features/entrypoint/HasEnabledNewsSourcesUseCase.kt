package com.voltek.newsfeed.features.entrypoint

import com.voltek.newsfeed.base.domain.UseCase
import io.reactivex.Single

class HasEnabledNewsSourcesUseCase : UseCase<Unit, Single<Boolean>> {

    override fun execute(param: Unit?): Single<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}