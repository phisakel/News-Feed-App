package gr.phisakel.newsfeed.dagger

import android.content.Context
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import dagger.Module
import dagger.Provides

@Module
class PlatformModule {

    @Provides
    fun provideResourcesManager(context: Context) = ResourcesManager(context)
}
