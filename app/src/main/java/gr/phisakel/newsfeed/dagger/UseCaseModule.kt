package gr.phisakel.newsfeed.dagger

import gr.phisakel.newsfeed.domain.repository.ArticlesRepository
import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.articles.GetArticlesUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.EnableNewsSourceUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUpdatesUseCase
import gr.phisakel.newsfeed.domain.usecase.newssources.NewsSourcesUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Singleton

@Module
class UseCaseModule(
        private val uiScheduler: Scheduler,
        private val ioScheduler: Scheduler,
        private val computationScheduler: Scheduler
) {

    @Provides
    fun provideGetArticlesInteractor(
            articlesRepository: ArticlesRepository,
            newsSourcesRepository: NewsSourcesRepository
    ) = GetArticlesUseCase(articlesRepository, newsSourcesRepository, ioScheduler, uiScheduler)

    @Provides
    fun provideNewsSourcesInteractor(newsSourcesRepository: NewsSourcesRepository)
            = NewsSourcesUseCase(newsSourcesRepository, ioScheduler, uiScheduler)

    @Provides
    fun provideEnableNewsSourceInteractor(newsSourcesRepository: NewsSourcesRepository)
            = EnableNewsSourceUseCase(newsSourcesRepository, computationScheduler, uiScheduler)

    @Provides
    @Singleton
    fun provideNewsSourcesUpdatesInteractor(newsSourcesRepository: NewsSourcesRepository)
            = NewsSourcesUpdatesUseCase(newsSourcesRepository, computationScheduler, uiScheduler)
}
