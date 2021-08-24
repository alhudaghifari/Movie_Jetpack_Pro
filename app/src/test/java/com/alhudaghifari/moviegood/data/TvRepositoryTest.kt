package com.alhudaghifari.moviegood.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alhudaghifari.moviegood.api.TvService
import com.alhudaghifari.moviegood.data.local.TvLocalDataSource
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import com.alhudaghifari.moviegood.data.remote.source.TvRemoteDataSource
import com.alhudaghifari.moviegood.utils.AppExecutors
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.vo.Resource
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
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
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
    private val dummyRemoteTv = DummyGenerator.generateRemoteDummyTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(TvRemoteDataSource::class.java)
    private val local = mock(TvLocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val repository = TvRepository(remote, local, appExecutors)

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
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getListTv()).thenReturn(dataSourceFactory)
        repository.getOnTheAir()

        val movieEntityList = Resource.success(PagedListUtil.mockPagedList(DummyGenerator.generateDummyTvShows()))
        Mockito.verify(local).getListTv()
        assertNotNull(movieEntityList)
        assertEquals(dummyRemoteTv.size, movieEntityList.data?.size)
    }

    @Test
    fun getFavoriteTv() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvEntity>
        `when`(local.getFavoriteTv()).thenReturn(dataSourceFactory)
        repository.getFavoriteTv()

        val movieEntityList = Resource.success(PagedListUtil.mockPagedList(DummyGenerator.generateDummyTvShows()))
        Mockito.verify(local).getFavoriteTv()
        assertNotNull(movieEntityList)
        assertEquals(dummyRemoteTv.size, movieEntityList.data?.size)
    }

    @Test
    fun getPopularTv() {
        val tv = MutableLiveData<Resource<List<TvItem>>>()
        val res = Resource.success(dummyTvItem)
        tv.value = res

        `when`(remote.getPopularTv(dummyIdTv)).thenReturn(tv)
        val data = LiveDataTestUtil.getValue(repository.getPopularTv(dummyIdTv))
        verify(remote).getPopularTv(dummyIdTv)

        assertNotNull(data)
        assertEquals(tv.value?.data?.size, data.data?.size)
    }

    @Test
    fun getDetailTv() {
        val movie = MutableLiveData<TvEntity>()
        val dummyMovieList = DummyGenerator.generateDummyTvShows()
        val dummyMovie = dummyMovieList[0]
        movie.postValue(dummyMovie)
        val idTv = dummyMovie.tvId

        `when`(local.getDetailTvById(idTv)).thenReturn(movie)
        repository.getDetailTv(idTv)

        val movieEntityList = Resource.success(PagedListUtil.mockPagedList(DummyGenerator.generateDummyTvShows()))
        Mockito.verify(local).getDetailTvById(idTv)
        assertNotNull(movieEntityList)
        assertEquals(dummyMovie, movieEntityList.data!![0]!!)
    }
}