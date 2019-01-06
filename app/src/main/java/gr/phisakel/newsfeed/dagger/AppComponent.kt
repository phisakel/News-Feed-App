package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.presentation.ui.details.DetailsFragment
import gr.phisakel.newsfeed.presentation.ui.list.ListFragment
import gr.phisakel.newsfeed.presentation.ui.newssources.NewsSourcesFragment
import gr.phisakel.newsfeed.presentation.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class,
        PlatformModule::class,
        PresenterModule::class,
        RepositoryModule::class,
        RouterModule::class,
        RouterModule::class,
        StorageModule::class,
        UseCaseModule::class,
        AnalyticsModule::class)
)
interface AppComponent {

    fun inject(activity: SplashActivity)

    fun inject(fragment: NewsSourcesFragment)
    fun inject(fragment: ListFragment)
    fun inject(fragment: DetailsFragment)
}
