package com.voltek.newsfeed.common.reactive

import com.voltek.newsfeed.base.reactive.RxSchedulers
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RxSchedulersImpl : RxSchedulers {
    override val ui: Scheduler = AndroidSchedulers.mainThread()
    override val io: Scheduler = Schedulers.io()
    override val computation: Scheduler = Schedulers.computation()
}
