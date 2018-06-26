package com.voltek.newsfeed

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.voltek.newsfeed.dagger.*
import com.voltek.newsfeed.dagger.legacy.AppModule
import com.voltek.newsfeed.dagger.legacy.NetworkModule
import com.voltek.newsfeed.dagger.legacy.RouterModule
import com.voltek.newsfeed.dagger.legacy.UseCaseModule
import com.voltek.newsfeed.data.network.BASE_URL
import com.voltek.newsfeed.presentation.navigation.RouterHolderOld
import com.voltek.newsfeed.presentation.navigation.proxy.NavigatorBinderOld
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import timber.log.Timber

class App : Application() {

    companion object {

        // Navigation
        private val routerHolder: RouterHolderOld = RouterHolderOld()

        fun getNavigatorBinder(): NavigatorBinderOld = routerHolder

        // DI
        lateinit var appComponent: AppComponent

        lateinit var component: AppComponent
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
        component = appComponent

        Realm.init(this)
        Hawk.init(this).build()
        Timber.plant(Timber.DebugTree())
    }
}
