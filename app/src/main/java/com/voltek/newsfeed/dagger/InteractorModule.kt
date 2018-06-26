package com.voltek.newsfeed.dagger

import com.voltek.newsfeed.features.entrypoint.domain.EntryPointInteractor
import dagger.Module
import dagger.Provides

@Module
class InteractorModule {

    @Provides
    fun provideEntryPointInteractor() = EntryPointInteractor()
}
