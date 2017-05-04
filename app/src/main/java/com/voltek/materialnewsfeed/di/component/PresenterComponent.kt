package com.voltek.materialnewsfeed.di.component

import com.voltek.materialnewsfeed.di.module.InteractorModule
import com.voltek.materialnewsfeed.di.module.RouterModule
import com.voltek.materialnewsfeed.ui.list.ListPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        RouterModule::class,
        InteractorModule::class)
)
interface PresenterComponent {

    fun inject(presenter: ListPresenter)
}
