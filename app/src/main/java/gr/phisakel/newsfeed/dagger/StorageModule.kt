package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.data.storage.NewsSourcesStorage
import dagger.Module
import dagger.Provides

@Module
class StorageModule {

    @Provides
    fun provideNewsSourcesStorage(): NewsSourcesStorage = NewsSourcesStorage()
}
