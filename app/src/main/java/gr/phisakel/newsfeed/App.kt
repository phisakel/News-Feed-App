package gr.phisakel.newsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import gr.phisakel.newsfeed.dagger.*
import gr.phisakel.newsfeed.data.network.BASE_URL
import gr.phisakel.newsfeed.presentation.navigation.RouterHolder
import gr.phisakel.newsfeed.presentation.navigation.proxy.NavigatorBinder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import timber.log.Timber

class App : Application() {

    companion object {

        // Navigation
        private val routerHolder: RouterHolder = RouterHolder()

        fun getNavigatorBinder(): NavigatorBinder = routerHolder

        // DI
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        // Dependency injection
        val appModule = AppModule(this)
        val interactorModule = UseCaseModule(
                AndroidSchedulers.mainThread(),
                Schedulers.io(),
                Schedulers.computation())
        val networkModule = NetworkModule(BASE_URL)
        val routerModule = RouterModule(routerHolder)

        appComponent = DaggerAppComponent.builder()
                .routerModule(routerModule)
                .useCaseModule(interactorModule)
                .networkModule(networkModule)
                .appModule(appModule)
                .build()

        Realm.init(this)
        Hawk.init(this).build()
        Timber.plant(Timber.DebugTree())
    }
}
