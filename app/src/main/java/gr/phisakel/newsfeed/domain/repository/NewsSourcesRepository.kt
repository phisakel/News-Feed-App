package gr.phisakel.newsfeed.domain.repository

import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.data.storage.entity.SourceDB
import gr.phisakel.newsfeed.data.network.entity.SourceAPI
import gr.phisakel.newsfeed.data.network.NewsApi
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import gr.phisakel.newsfeed.data.storage.NewsSourcesStorage
import gr.phisakel.newsfeed.domain.Mapper
import gr.phisakel.newsfeed.domain.exception.NoConnectionException
import gr.phisakel.newsfeed.domain.exception.WrongNewsSourceIdException
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject

class NewsSourcesRepository(
        private val api: NewsApi,
        private val storage: NewsSourcesStorage,
        private val res: ResourcesManager
) {

    // Notifies subscribers when enabled news sources has changed
    private var mSourcesEnabledSubject: PublishSubject<Unit> = PublishSubject.create()

    fun getSourcesEnabledObservable(): Observable<Unit> = mSourcesEnabledSubject

    fun getAll(): Observable<Result<List<SourceUI>?>> = Observable.create {
        val emitter = it

        val sourcesCache = storage.queryAll()

        if (sourcesCache.isEmpty()) {
            api.fetchSources()
                    .map { it.sources }
                    .map { it.map { Mapper.sourceAPItoDB(it) } }
                    .subscribe({
                        storage.save(it)
                        emitter.onNext(Result(storage.queryAll().map { Mapper.sourceDBtoUI(it) }))
                    }, {
                        val message: String = when (it) {
                            is NoConnectionException -> res.getString(R.string.error_no_connection)
                            else -> res.getString(R.string.error_request_failed)
                        }
                        emitter.onNext(Result(null, message))
                    })
        } else {
            emitter.onNext(Result(sourcesCache.map { Mapper.sourceDBtoUI(it) }))
        }

        emitter.onComplete()
    }

    fun getCategory(category: String): Single<Result<List<SourceUI>?>> = Single.create {
        val emitter = it

        val query: List<SourceDB>
        var message = ""

        if (category == res.getString(R.string.category_all)) {
            query = storage.queryAll()

            if (query.isEmpty())
                message = res.getString(R.string.error_no_news_sources_loaded)
        } else if (category == res.getString(R.string.category_enabled) || category.isEmpty()) {
            query = storage.queryEnabled()

            if (query.isEmpty())
                message = res.getString(R.string.error_no_news_sources_selected_yet)
        } else {
            query = storage.queryCategory(category.toLowerCase())

            if (query.isEmpty())
                message = res.getString(R.string.error_no_news_sources_for_category)
        }

        emitter.onSuccess(Result(query.map { Mapper.sourceDBtoUI(it) }, message))
    }

    fun refresh(): Single<Result<List<SourceUI>?>> =
            api.fetchSources()
                    .map { it.sources }
                    .doOnSuccess(this::cacheSources)
                    .map { Result<List<SourceUI>?>() }
                    .onErrorReturn {
                        val message: String = when (it) {
                            is NoConnectionException -> res.getString(R.string.error_no_connection)
                            else -> res.getString(R.string.error_request_failed)
                        }
                        Result(null, message)
                    }
                    .flatMap {
                        Single.fromCallable {
                            Result(storage.queryAll().map { Mapper.sourceDBtoUI(it) }, it.message)
                        }
                    }

    fun update(id: String, isEnabled: Boolean): Completable = Completable.create {
        val emitter = it

        val source = storage.findById(id)

        if (source != null) {
            source.isEnabled = isEnabled
            storage.save(arrayListOf(source))
            mSourcesEnabledSubject.onNext(Unit)
            emitter.onComplete()
        } else {
            emitter.onError(WrongNewsSourceIdException())
        }
    }

    private fun cacheSources(sources: List<SourceAPI>) {
        val current = storage.queryEnabled()
        val new = (sources as ArrayList).map { Mapper.sourceAPItoDB(it) }

        for (source in new)
            for (enabled in current)
                if (source.id == enabled.id)
                    source.isEnabled = true

        storage.deleteAll()
        storage.save(new)
    }
}
