package com.alhudaghifari.moviegood.ui.detail_tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.utils.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
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
class DetailTvViewModelTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: TvService
    private lateinit var viewModel: DetailTvViewModel
    private val contentRecommendationTv = MockResponseFileReader("tv_asset.json").content
    private val contentDetailTv = MockResponseFileReader("tv_detail_asset.json").content
    private lateinit var dummyTv: TvResponse
    private lateinit var dummyTvItem: List<TvItem>
    private lateinit var dummyDetailTv: TvDetailResponse
    private var dummyIdTv: Int = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observerTv: Observer<Resource<List<TvItem>>>

    @Mock
    private lateinit var observerDetailTv: Observer<Resource<TvDetailResponse>>

    @Before
    fun setUp() {
        val gson = Gson()
        dummyTv = gson.fromJson(contentRecommendationTv, TvResponse::class.java)
        dummyTvItem = dummyTv.tvItems!!
        dummyIdTv = dummyTv.tvItems?.get(0)?.id ?: 95281
        dummyDetailTv = gson.fromJson(contentDetailTv, TvDetailResponse::class.java)

        server.start(MOCK_WEBSERVER_PORT)
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(TvService::class.java)
        viewModel = DetailTvViewModel(repository)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Tes response recommendation tv using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentRecommendationTv)
        server.enqueue(successResponse)

        val response = service.getPopular().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyTv)
        assertEquals(dummyTv.tvItems?.size ?: -1, responseBody?.tvItems?.size ?: 0,)
    }

    @Test
    fun `Tes response detail tv using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentDetailTv)
        server.enqueue(successResponse)
        val dummyId = dummyDetailTv.id ?: 459151

        val response = service.getDetailTv(dummyId.toString()).execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyDetailTv)
        assertEquals(dummyDetailTv.genres?.size, responseBody?.genres?.size ?: 0)
    }

    @Test
    fun `Tes getRecommendationMovie view model and repository`() {
        val movie = MutableLiveData<Resource<List<TvItem>>>()
        val res = Resource.success(dummyTvItem)
        movie.value = res

        Mockito.`when`(repository.getPopularTv(dummyIdTv)).thenReturn(movie)
        val movieData = viewModel.getRecommendationTv(dummyIdTv).value
        verify(repository).getPopularTv(dummyIdTv)

        viewModel.getRecommendationTv(dummyIdTv).observeForever(observerTv)
        verify(observerTv).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyTvItem.size, movieData?.data?.size ?: 0)
    }

    @Test
    fun `Tes getDetailMovie view model and repository`() {
        val movie = MutableLiveData<Resource<TvDetailResponse>>()
        val res = Resource.success(dummyDetailTv)
        movie.value = res

        Mockito.`when`(repository.getDetailTv(dummyIdTv.toString())).thenReturn(movie)
        val movieData = viewModel.getDetailTv(dummyIdTv.toString()).value
        verify(repository).getDetailTv(dummyIdTv.toString())

        viewModel.getDetailTv(dummyIdTv.toString()).observeForever(observerDetailTv)
        verify(observerDetailTv).onChanged(res)

        assertNotNull(movieData)

        assertEquals(movieData?.data?.name, dummyDetailTv.name)
        assertEquals(movieData?.data?.genres?.size, dummyDetailTv.genres?.size ?: 1)
        assertEquals(movieData?.data?.tagline, dummyDetailTv.tagline)
    }
}