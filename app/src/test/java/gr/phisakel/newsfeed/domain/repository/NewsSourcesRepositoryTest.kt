package gr.phisakel.newsfeed.domain.repository

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import gr.phisakel.newsfeed.R
import gr.phisakel.newsfeed.MockData
import gr.phisakel.newsfeed.data.network.NewsApi
import gr.phisakel.newsfeed.data.network.entity.NewsApiSourcesResponse
import gr.phisakel.newsfeed.data.platform.ResourcesManager
import gr.phisakel.newsfeed.data.storage.NewsSourcesStorage
import gr.phisakel.newsfeed.domain.exception.NoConnectionException
import gr.phisakel.newsfeed.domain.exception.WrongNewsSourceIdException
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsSourcesRepositoryTest {

    private lateinit var newsSourcesRepo: NewsSourcesRepository

    private val stringNoConnection = MockData.stringNoConnection
    private val stringServerError = MockData.stringServerError
    private val stringCategoryAll = "stringCategoryAll"
    private val stringCategoryEnabled = "stringCategoryEnabled"
    private val stringNoNewsSourcesLoaded = "stringNoNewsSourcesLoaded"
    private val stringNoNewsSourcesSelectedYet = "stringNoNewsSourcesSelectedYet"
    private val stringNoNewsSourcesForCategory = "stringNoNewsSourcesForCategory"

    private val responseSources = NewsApiSourcesResponse(arrayListOf(MockData.sourceAPI()))

    private val sourceDB = MockData.sourceDB()

    @Mock
    lateinit var api: NewsApi

    @Mock
    lateinit var storage: NewsSourcesStorage

    @Mock
    lateinit var res: ResourcesManager

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        newsSourcesRepo = NewsSourcesRepository(api, storage, res)

        whenever(res.getString(R.string.error_no_connection))
                .thenReturn(stringNoConnection)
        whenever(res.getString(R.string.error_request_failed))
                .thenReturn(stringServerError)
        whenever(res.getString(R.string.category_all))
                .thenReturn(stringCategoryAll)
        whenever(res.getString(R.string.category_enabled))
                .thenReturn(stringCategoryEnabled)
        whenever(res.getString(R.string.error_no_news_sources_loaded))
                .thenReturn(stringNoNewsSourcesLoaded)
        whenever(res.getString(R.string.error_no_news_sources_selected_yet))
                .thenReturn(stringNoNewsSourcesSelectedYet)
        whenever(res.getString(R.string.error_no_news_sources_for_category))
                .thenReturn(stringNoNewsSourcesForCategory)
    }

    @Test
    fun updateWithWrongID() {
        val id = "10"
        whenever(storage.findById(id)).thenReturn(null)
        newsSourcesRepo.update(id, false)
                .test()
                .assertError { it is WrongNewsSourceIdException }
        verify(storage).findById(id)
    }

    @Test
    fun updateNormal() {
        val id = "10"
        whenever(storage.findById(id)).thenReturn(sourceDB)
        newsSourcesRepo.update(id, false)
                .test()
                .assertComplete()
        verify(storage).findById(id)
        verify(storage).save(arrayListOf(sourceDB))
    }

    @Test
    fun getAllWithEmptyCache() {
        whenever(api.fetchSources()).thenReturn(Single.just(responseSources))
        whenever(storage.queryAll()).thenReturn(ArrayList())
        newsSourcesRepo.getAll()
                .test()
                .assertNoErrors()
        verify(storage, times(2)).queryAll()
        verify(storage).save(any())
        verify(api).fetchSources()
    }

    @Test
    fun getAllWithCache() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
        verify(storage).queryAll()
        verify(storage, times(0)).save(any())
        verify(api, times(0)).fetchSources()
    }

    @Test
    fun getAllWIthAPIErrors() {
        whenever(storage.queryAll()).thenReturn(ArrayList())
        whenever(api.fetchSources()).thenReturn(Single.error(NoConnectionException()))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message == stringNoConnection }
                .assertValue { it.data == null }
                .assertComplete()
        whenever(api.fetchSources()).thenReturn(Single.error(Exception()))
        newsSourcesRepo.getAll()
                .test()
                .assertValue { it.message == stringServerError }
                .assertValue { it.data == null }
                .assertComplete()
        verify(res).getString(R.string.error_no_connection)
        verify(res).getString(R.string.error_request_failed)
        verify(storage, times(2)).queryAll()
        verify(storage, times(0)).save(any())
        verify(api, times(2)).fetchSources()
    }

    @Test
    fun getRandomCategory() {
        val category = "category"
        whenever(storage.queryCategory(category)).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getCategory(category)
                .test()
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(storage).queryCategory(category)
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getWrongCategory() {
        val category = "category"
        whenever(storage.queryCategory(category)).thenReturn(ArrayList())
        newsSourcesRepo.getCategory(category)
                .test()
                .assertValue { it.message == stringNoNewsSourcesForCategory }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()
        verify(res).getString(R.string.error_no_news_sources_for_category)
        verify(storage).queryCategory(category)
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getAllCategoryNormal() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        newsSourcesRepo.getCategory(stringCategoryAll)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(storage).queryAll()
        verify(storage, times(0)).queryCategory(anyString())
    }

    @Test
    fun getAllCategoryEmpty() {
        whenever(storage.queryAll()).thenReturn(ArrayList())
        newsSourcesRepo.getCategory(stringCategoryAll)
                .test()
                .assertValue { it.message == stringNoNewsSourcesLoaded }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()
        verify(res).getString(R.string.error_no_news_sources_loaded)
        verify(storage).queryAll()
        verify(storage, times(0)).queryCategory(anyString())
    }

    @Test
    fun getEnabledCategoryNormal() {
        whenever(storage.queryEnabled()).thenReturn(arrayListOf(sourceDB))

        newsSourcesRepo.getCategory(stringCategoryEnabled)
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()

        newsSourcesRepo.getCategory("")
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()

        verify(storage, times(2)).queryEnabled()
        verify(storage, times(0)).queryCategory(anyString())
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun getEnabledCategoryEmpty() {
        whenever(storage.queryEnabled()).thenReturn(arrayListOf())

        newsSourcesRepo.getCategory(stringCategoryEnabled)
                .test()
                .assertValue { it.message == stringNoNewsSourcesSelectedYet }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()

        newsSourcesRepo.getCategory("")
                .test()
                .assertValue { it.message == stringNoNewsSourcesSelectedYet }
                .assertValue { it.data!!.isEmpty() }
                .assertComplete()

        verify(res, times(2)).getString(R.string.error_no_news_sources_selected_yet)
        verify(storage, times(2)).queryEnabled()
        verify(storage, times(0)).queryCategory(anyString())
        verify(storage, times(0)).queryAll()
    }

    @Test
    fun refreshWithAPIErrors() {
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        whenever(api.fetchSources()).thenReturn(Single.error(NoConnectionException()))
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertValue { it.message == stringNoConnection }
                .assertComplete()

        whenever(storage.queryAll()).thenReturn(ArrayList())
        whenever(api.fetchSources()).thenReturn(Single.error(Exception()))
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.data!!.isEmpty() }
                .assertValue { it.message == stringServerError }
                .assertComplete()

        verify(api, times(2)).fetchSources()
        verify(res).getString(R.string.error_no_connection)
        verify(res).getString(R.string.error_request_failed)
        verify(storage, times(2)).queryAll()
        verify(storage, times(0)).queryEnabled()
        verify(storage, times(0)).save(any())
        verify(storage, times(0)).deleteAll()
    }

    @Test
    fun refreshNormal() {
        whenever(api.fetchSources()).thenReturn(Single.just(responseSources))
        whenever(storage.queryAll()).thenReturn(arrayListOf(sourceDB))
        whenever(storage.queryEnabled()).thenReturn(ArrayList())
        newsSourcesRepo.refresh()
                .test()
                .assertValue { it.message.isEmpty() }
                .assertValue { it.data!![0].id == sourceDB.id }
                .assertComplete()
        verify(api).fetchSources()
        verify(res, times(0)).getString(anyInt())
        verify(storage).queryAll()
        verify(storage).queryEnabled()
        verify(storage).save(any())
        verify(storage).deleteAll()
    }
}
