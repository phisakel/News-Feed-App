package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.base.reactive.RxSchedulers
import com.voltek.newsfeed.common.reactive.RxSchedulersImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RxModule {

    @Provides
    @Singleton
    fun provideRxSchedulers(): RxSchedulers = RxSchedulersImpl()
}
