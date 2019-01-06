package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.data.network.NewsApi
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import gr.phisakel.newsfeed.data.storage.NewsSourcesStorage
import gr.phisakel.newsfeed.domain.repository.ArticlesRepository
import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    fun provideArticlesRepository(newsApi: NewsApi, resourcesManager: ResourcesManager)
            = ArticlesRepository(newsApi, resourcesManager)

    @Provides
    @Singleton
    fun provideNewsSourcesRepository(
            newsApi: NewsApi,
            resourcesManager: ResourcesManager,
            newsSourcesStorage: NewsSourcesStorage
    ) = NewsSourcesRepository(newsApi, newsSourcesStorage, resourcesManager)
}
