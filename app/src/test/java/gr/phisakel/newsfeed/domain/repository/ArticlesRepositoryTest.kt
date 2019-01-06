package gr.phisakel.newsfeed.domain.repository

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.MockData
import gr.phisakel.newsfeed.data.network.NewsApi
import gr.phisakel.newsfeed.data.network.entity.NewsApiArticlesResponse
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import gr.phisakel.newsfeed.domain.exception.NoConnectionException
import gr.phisakel.newsfeed.domain.exception.NoNewsSourcesSelectedException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticlesRepositoryTest {

    private lateinit var articlesRepo: ArticlesRepository

    private val stringNoConnection = MockData.stringNoConnection
    private val stringServerError = MockData.stringServerError

    private val sources = arrayListOf(MockData.sourceUI())

    private val response = NewsApiArticlesResponse(arrayListOf(MockData.articleAPI()))

    @Mock
    lateinit var api: NewsApi

    @Mock
    lateinit var res: ResourcesManager

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        articlesRepo = ArticlesRepository(api, res)
        whenever(res.getString(R.string.error_no_connection)).thenReturn(stringNoConnection)
        whenever(res.getString(R.string.error_retrieve_failed)).thenReturn(stringServerError)
    }

    @Test
    fun noSourcesSelected() {
        articlesRepo.get(ArrayList())
                .test()
                .assertError { it is NoNewsSourcesSelectedException }
        verify(api, times(0)).fetchArticles(anyString())
    }

    @Test
    fun apiExceptions() {
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(NoConnectionException()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == stringNoConnection }
                .assertComplete()
        verify(api).fetchArticles(anyString())
        whenever(api.fetchArticles(anyString())).thenReturn(Single.error(Exception()))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.data == null }
                .assertValue { it.message == (stringServerError + sources[0].name) }
                .assertComplete()
        verify(api, times(2)).fetchArticles(anyString())
    }

    @Test
    fun normalServerResponse() {
        whenever(api.fetchArticles(anyString())).thenReturn(Single.just(response))
        articlesRepo.get(sources)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data?.size == 2 } // Because of Header
                .assertValue { it.data!![1].title == response.articles[0].title }
                .assertComplete()
        verify(api).fetchArticles(anyString())
    }
}
