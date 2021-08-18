package com.alhudaghifari.moviegood.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.remote.*
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.utils.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
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
class TvRepositoryTest {


    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: TvService
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
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Test remote source API recommendation TV using Mock Web Server`() {
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
    fun `Test remote source API detail TV using Mock Web Server`() {
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
    fun `Test remote source API On the Air TV using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentRecommendationTv)
        server.enqueue(successResponse)

        val response = service.getOnTheAir().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyTv)
        assertEquals(dummyTv.tvItems?.size ?: -1, responseBody?.tvItems?.size ?: 0)
    }

    @Test
    fun getOnTheAir() {
        val tv = MutableLiveData<Resource<TvResponse>>()
        val res = Resource.success(dummyTv)
        tv.value = res

        Mockito.`when`(repository.getOnTheAir()).thenReturn(tv)
        val data = LiveDataTestUtil.getValue(repository.getOnTheAir())
        verify(repository).getOnTheAir()

        assertNotNull(data)
        assertEquals(tv.value?.data?.tvItems?.size, data.data?.tvItems?.size)
    }

    @Test
    fun getPopularTv() {
        val tv = MutableLiveData<Resource<List<TvItem>>>()
        val res = Resource.success(dummyTvItem)
        tv.value = res

        Mockito.`when`(repository.getPopularTv(dummyIdTv)).thenReturn(tv)
        val data = LiveDataTestUtil.getValue(repository.getPopularTv(dummyIdTv))
        verify(repository).getPopularTv(dummyIdTv)

        assertNotNull(data)
        assertEquals(tv.value?.data?.size, data.data?.size)
    }

    @Test
    fun getDetailTv() {
        val tv = MutableLiveData<Resource<TvDetailResponse>>()
        val res = Resource.success(dummyDetailTv)
        tv.value = res

        Mockito.`when`(repository.getDetailTv(dummyIdTv.toString())).thenReturn(tv)
        val data = LiveDataTestUtil.getValue(repository.getDetailTv(dummyIdTv.toString()))
        verify(repository).getDetailTv(dummyIdTv.toString())

        assertNotNull(data)
        assertEquals(tv.value?.data?.genres?.size, data.data?.genres?.size)
    }
}