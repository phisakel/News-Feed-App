package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.base.navigation.NavigationBuffer
import com.voltek.newsfeed.base.navigation.NavigatorHolder
import com.voltek.newsfeed.base.navigation.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NavigationModule {

    @Provides
    @Singleton
    fun provideNavigationBuffer() = NavigationBuffer()

    @Provides
    @Singleton
    fun provideRouter(navigationBuffer: NavigationBuffer): Router = navigationBuffer

    @Provides
    @Singleton
    fun provideNavigationHolder(navigationBuffer: NavigationBuffer): NavigatorHolder = navigationBuffer
}
