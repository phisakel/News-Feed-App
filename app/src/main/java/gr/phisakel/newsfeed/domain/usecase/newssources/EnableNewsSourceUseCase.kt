package gr.phisakel.newsfeed.domain.usecase.newssources

import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.BaseUseCase
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import io.reactivex.Observable
import io.reactivex.Scheduler

class EnableNewsSourceUseCase(
        private val newsSourcesRepository: NewsSourcesRepository,
        jobScheduler: Scheduler, uiScheduler: Scheduler
) : BaseUseCase<Unit, SourceUI>(jobScheduler, uiScheduler) {

    override fun buildObservable(parameter: Parameter<SourceUI?>): Observable<Result<Unit>> {
        if (parameter.item != null) {
            val source = parameter.item
            return newsSourcesRepository
                    .update(source.id, !source.isEnabled)
                    .toObservable<Result<Unit>>()
                    .map { Result(null) }
        } else {
            return Observable.error { NullPointerException() }
        }
    }
}
