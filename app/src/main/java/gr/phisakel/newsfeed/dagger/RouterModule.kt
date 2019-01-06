package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.presentation.navigation.RouterHolder
import gr.phisakel.newsfeed.presentation.navigation.proxy.Router
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RouterModule(private val routerHolder: RouterHolder) {

    @Provides
    @Singleton
    fun provideRouter(): Router = routerHolder
}
