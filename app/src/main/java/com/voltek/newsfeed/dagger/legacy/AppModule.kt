package com.voltek.newsfeed.dagger.legacy

import android.app.Application
import android.content.Context
import com.voltek.newsfeed.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val app: App) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideApplication(): Application = app
}
