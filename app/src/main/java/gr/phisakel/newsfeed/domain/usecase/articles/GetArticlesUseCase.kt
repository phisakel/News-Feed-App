package gr.phisakel.newsfeed.domain.usecase.articles

import gr.phisakel.newsfeed.domain.repository.ArticlesRepository
import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.BaseUseCase
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetArticlesUseCase constructor(
        private val articlesRepository: ArticlesRepository,
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
) : BaseUseCase<List<ArticleUI>?, ArticleUI>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Parameter<ArticleUI?>): Observable<Result<List<ArticleUI>?>> =
            newsSourcesRepository
                    .getCategory("")
                    .toObservable()
                    .flatMap { articlesRepository.get(it.data ?: ArrayList()) }
}
