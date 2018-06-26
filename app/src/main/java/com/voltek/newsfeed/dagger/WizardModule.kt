package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.base.navigation.Router
import com.voltek.newsfeed.base.reactive.RxSchedulers
import com.voltek.newsfeed.features.entrypoint.domain.EntryPointInteractor
import com.voltek.newsfeed.features.entrypoint.presentation.EntryPointWizard
import dagger.Module
import dagger.Provides

@Module
class WizardModule {

    @Provides
    fun provideEntryPointWizard(
            entryPointInteractor: EntryPointInteractor,
            router: Router,
            rxSchedulers: RxSchedulers
    ) = EntryPointWizard(entryPointInteractor, router, rxSchedulers)
}
