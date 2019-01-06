package gr.phisakel.newsfeed.domain.usecase.newssources

import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.BaseUseCase
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import io.reactivex.Observable
import io.reactivex.Scheduler

class NewsSourcesUseCase(
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
) : BaseUseCase<List<SourceUI>?, Unit>(jobScheduler, uiScheduler) {

    companion object {
        // Flags, using for specifying type of performing operation
        const val GET = "GET"
        const val REFRESH = "REFRESH"
    }

    override fun buildObservable(parameter: Parameter<Unit?>): Observable<Result<List<SourceUI>?>> {
        if (parameter.flag == REFRESH) {
            return newsSourcesRepository
                    .refresh().toObservable()
        } else if (parameter.flag == GET) {
            return newsSourcesRepository
                    .getAll()
        } else {
            return newsSourcesRepository
                    .getCategory(parameter.flag)
                    .toObservable()
        }
    }
}
