package com.alhudaghifari.moviegood.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.utils.Resource
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {
    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: TvService
    private lateinit var viewModel: TvShowsViewModel
    private val contentMovie = MockResponseFileReader("tv_asset.json").content
    private lateinit var dummyTv: TvResponse

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvResponse>>

    @Before
    fun setUp() {
        val gson = Gson()
        dummyTv = gson.fromJson(contentMovie, TvResponse::class.java)

        server.start(MOCK_WEBSERVER_PORT)
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(TvService::class.java)
        viewModel = TvShowsViewModel(repository)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Tes response get tv shows using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentMovie)
        server.enqueue(successResponse)

        val response = service.getOnTheAir().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyTv)
        assertEquals(dummyTv.tvItems?.size ?: -1, responseBody?.tvItems?.size ?: 0)
    }

    @Test
    fun `Tes get tv shows view model and repository`() {
        val movie = MutableLiveData<Resource<TvResponse>>()
        val res = Resource.success(dummyTv)
        movie.value = res

        Mockito.`when`(repository.getOnTheAir()).thenReturn(movie)
        val movieData = viewModel.getTvShows().value
        Mockito.verify(repository).getOnTheAir()

        viewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyTv.tvItems?.size ?: -1, movieData?.data?.tvItems?.size ?: 0)
    }
}