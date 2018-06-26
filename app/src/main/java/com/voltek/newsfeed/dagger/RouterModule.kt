package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.presentation.navigation.RouterHolderOld
import com.voltek.newsfeed.presentation.navigation.proxy.RouterOld
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule(private val routerHolder: RouterHolderOld) {

    @Provides
    @Singleton
    fun provideRouter(): RouterOld = routerHolder
}
