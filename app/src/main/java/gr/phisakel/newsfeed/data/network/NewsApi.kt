package gr.phisakel.newsfeed.data.network

import gr.phisakel.newsfeed.data.network.entity.NewsApiArticlesResponse
import gr.phisakel.newsfeed.data.network.entity.NewsApiSourcesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET(ENDPOINT_ARTICLES)
    fun fetchArticles(
            @Query(PARAM_SOURCE) source: String
    ): Single<NewsApiArticlesResponse>

    @GET(ENDPOINT_ARTICLES)
    fun fetchCountryLatest(
            @Query(COUNTRY_SOURCE) countryCode: String,
            @Query(COUNTRY_CATEGORY) category: String
    ): Single<NewsApiArticlesResponse>

    @GET(ENDPOINT_SOURCES)
    fun fetchSources(): Single<NewsApiSourcesResponse>
}
