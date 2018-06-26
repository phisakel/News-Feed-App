package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.dagger.legacy.*
import com.voltek.newsfeed.features.entrypoint.presentation.EntryPointActivity
import com.voltek.newsfeed.features.newssource.presentation.NewsSourceFragment
import com.voltek.newsfeed.presentation.ui.details.DetailsFragment
import com.voltek.newsfeed.presentation.ui.list.ListFragment
import com.voltek.newsfeed.presentation.ui.newssources.NewsSourcesFragment
import com.voltek.newsfeed.presentation.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    NetworkModule::class,
    PlatformModule::class,
    PresenterModule::class,
    RepositoryModule::class,
    RouterModule::class,
    RouterModule::class,
    StorageModule::class,
    UseCaseModule::class,
    AnalyticsModule::class,
    NavigationModule::class,
    RxModule::class,
    WizardModule::class,
    InteractorModule::class
])
interface AppComponent {

    fun inject(activity: SplashActivity)

    fun inject(fragment: NewsSourcesFragment)
    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailsFragment)

    /* ------------------------------------------------- */

    fun inject(entryPointActivity: EntryPointActivity)
    fun inject(newsSourceFragment: NewsSourceFragment)
}
