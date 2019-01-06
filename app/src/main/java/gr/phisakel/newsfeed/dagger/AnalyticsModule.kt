package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.BuildConfig
import gr.phisakel.newsfeed.analytics.Analytics
import gr.phisakel.newsfeed.analytics.AnalyticsKit
import gr.phisakel.newsfeed.analytics.ComposedAnalytics
import gr.phisakel.newsfeed.analytics.kits.DebugAnalyticsKit
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AnalyticsModule {

    @Provides
    fun provideDebugAnalyticsKit() = DebugAnalyticsKit()

    @Provides
    @Singleton
    fun provideAnalytics(debugAnalyticsKit: DebugAnalyticsKit): Analytics {
        val kits = mutableListOf<AnalyticsKit>()
        if (BuildConfig.DEBUG) kits.add(debugAnalyticsKit)
        return ComposedAnalytics(kits)
    }
}
