package gr.phisakel.newsfeed.domain.repository

import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.data.network.NewsApi
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import gr.phisakel.newsfeed.domain.Mapper
import gr.phisakel.newsfeed.domain.exception.NoConnectionException
import gr.phisakel.newsfeed.domain.exception.NoNewsSourcesSelectedException
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.ArticleUI
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import io.reactivex.Observable

class ArticlesRepository(private val api: NewsApi, private val res: ResourcesManager) {

    fun get(sources: List<SourceUI>): Observable<Result<List<ArticleUI>?>> = Observable.create {
        val emitter = it

        if (!sources.isEmpty()) {
            for (source in sources) {
                val articles = if(source.id.contains("_country_") == false)
                    api.fetchArticles(source.id)
                    else api.fetchCountryLatest(source.country)
                articles.subscribe({
                            val result = ArrayList<ArticleUI>()
                            val sourceTitle = ArticleUI()
                            result.add(sourceTitle)
                            result.addAll(it.articles.map { Mapper.articleAPItoUI(it) })
                            result.forEach { article -> article.source = source.name }
                            emitter.onNext(Result(result))
                        }, {
                            val message: String = when (it) {
                                is NoConnectionException -> res.getString(R.string.error_no_connection)
                                else -> res.getString(R.string.error_retrieve_failed) + source.name
                            }
                            emitter.onNext(Result(null, message))
                        })
            }
        } else {
            emitter.onError(NoNewsSourcesSelectedException(
                    res.getString(R.string.error_no_news_sources_selected)))
        }

        emitter.onComplete()
    }
}
