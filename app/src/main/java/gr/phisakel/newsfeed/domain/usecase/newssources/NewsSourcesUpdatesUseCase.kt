package gr.phisakel.newsfeed.domain.usecase.newssources

import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.BaseUseCase
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.Result
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit

/**
 * Notify presenter, if enabled news sources has changed.
 */
class NewsSourcesUpdatesUseCase(
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
)  : BaseUseCase<Unit?, Unit>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Parameter<Unit?>): Observable<Result<Unit?>> =
            newsSourcesRepository
                    .getSourcesEnabledObservable()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map { Result(null) }
}
