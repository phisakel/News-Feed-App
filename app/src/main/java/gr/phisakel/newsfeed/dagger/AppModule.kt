package gr.phisakel.newsfeed.dagger

import android.content.Context
import gr.phisakel.newsfeed.App
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
    fun provideApplication(): App = app
}
