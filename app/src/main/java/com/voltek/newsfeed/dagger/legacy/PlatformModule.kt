package com.voltek.newsfeed.dagger.legacy

import android.content.Context
import com.voltek.newsfeed.data.platform.ResourcesManager
import dagger.Module
import dagger.Provides

@Module
class PlatformModule {

    @Provides
    fun provideResourcesManager(context: Context) = ResourcesManager(context)
}
