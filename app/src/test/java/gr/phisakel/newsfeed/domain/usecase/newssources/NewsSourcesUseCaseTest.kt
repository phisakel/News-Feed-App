package gr.phisakel.newsfeed.domain.usecase.newssources

import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import gr.phisakel.newsfeed.MockData
import gr.phisakel.newsfeed.domain.repository.NewsSourcesRepository
import gr.phisakel.newsfeed.domain.usecase.BaseUseCaseTest
import gr.phisakel.newsfeed.domain.usecase.Parameter
import gr.phisakel.newsfeed.domain.usecase.Result
import gr.phisakel.newsfeed.presentation.entity.SourceUI
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class NewsSourcesUseCaseTest : BaseUseCaseTest() {

    private lateinit var newsSourcesUseCase: NewsSourcesUseCase

    private val sourceUI = MockData.sourceUI()

    @Mock
    lateinit var newsSourcesRepository: NewsSourcesRepository

    @Before
    fun prepare() {
        MockitoAnnotations.initMocks(this)
        newsSourcesUseCase = NewsSourcesUseCase(newsSourcesRepository, scheduler, scheduler)
    }

    @Test
    fun executeWithRefreshFlag() {
        whenever(newsSourcesRepository.refresh())
                .thenReturn(Single.just(Result(data = arrayListOf(sourceUI))))
        var error = false
        var completed = false
        var emittedResult = ArrayList<SourceUI>()
        var emittedMessage = ""
        newsSourcesUseCase.execute(
                Parameter(flag = NewsSourcesUseCase.REFRESH),
                Consumer {
                    emittedResult = it.data as ArrayList<SourceUI>
                    emittedMessage = it.message
                },
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        Assert.assertFalse(error)
        Assert.assertTrue(completed)
        Assert.assertEquals(sourceUI.id, emittedResult[0].id)
        Assert.assertTrue(emittedMessage.isEmpty())
        verify(newsSourcesRepository).refresh()
        verify(newsSourcesRepository, times(0)).getAll()
        verify(newsSourcesRepository, times(0)).getCategory(anyString())
    }

    @Test
    fun executeWithGetFlag() {
        whenever(newsSourcesRepository.getAll())
                .thenReturn(Observable.just(Result(data = arrayListOf(sourceUI))))
        var error = false
        var completed = false
        var emittedResult = ArrayList<SourceUI>()
        var emittedMessage = ""
        newsSourcesUseCase.execute(
                Parameter(flag = NewsSourcesUseCase.GET),
                Consumer {
                    emittedResult = it.data as ArrayList<SourceUI>
                    emittedMessage = it.message
                },
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        Assert.assertFalse(error)
        Assert.assertTrue(completed)
        Assert.assertEquals(sourceUI.id, emittedResult[0].id)
        Assert.assertTrue(emittedMessage.isEmpty())
        verify(newsSourcesRepository, times(0)).refresh()
        verify(newsSourcesRepository).getAll()
        verify(newsSourcesRepository, times(0)).getCategory(anyString())
    }

    @Test
    fun executeForCategory() {
        val category = "category"
        whenever(newsSourcesRepository.getCategory(category))
                .thenReturn(Single.just(Result(data = arrayListOf(sourceUI))))
        var error = false
        var completed = false
        var emittedResult = ArrayList<SourceUI>()
        var emittedMessage = ""
        newsSourcesUseCase.execute(
                Parameter(flag = category),
                Consumer {
                    emittedResult = it.data as ArrayList<SourceUI>
                    emittedMessage = it.message
                },
                Consumer {
                    error = true
                },
                Action {
                    completed = true
                }
        )
        Assert.assertFalse(error)
        Assert.assertTrue(completed)
        Assert.assertEquals(sourceUI.id, emittedResult[0].id)
        Assert.assertTrue(emittedMessage.isEmpty())
        verify(newsSourcesRepository, times(0)).refresh()
        verify(newsSourcesRepository, times(0)).getAll()
        verify(newsSourcesRepository).getCategory(category)
    }
}
