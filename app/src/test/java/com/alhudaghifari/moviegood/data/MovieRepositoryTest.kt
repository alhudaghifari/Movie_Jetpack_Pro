package com.alhudaghifari.moviegood.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.data.local.MovieLocalDataSource
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.remote.source.MovieRemoteDataSource
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.utils.AppExecutors
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.vo.Resource
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class MovieRepositoryTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: MovieService
    private val contentRecommendationMovie = MockResponseFileReader("movie_asset.json").content
    private val contentDetailMovie = MockResponseFileReader("movie_detail_asset.json").content
    private lateinit var dummyMovie: MovieResponse
    private lateinit var dummyMovieItem: List<MovieItem>
    private lateinit var dummyDetailMovie: MovieDetailResponse
    private var dummyIdMovie: Int = 0
    private val dummyRemoteMovie = DummyGenerator.generateRemoteDummyMovies()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(MovieRemoteDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val local = mock(MovieLocalDataSource::class.java)
    private val repository = MovieRepository(remote, local, appExecutors)

    @Before
    fun setUp() {
        val gson = Gson()
        dummyMovie = gson.fromJson(contentRecommendationMovie, MovieResponse::class.java)
        dummyMovieItem = dummyMovie.results!!
        dummyIdMovie = dummyMovie.results?.get(0)?.id ?: 436969
        dummyDetailMovie = gson.fromJson(contentDetailMovie, MovieDetailResponse::class.java)

        server.start(MOCK_WEBSERVER_PORT)
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MovieService::class.java)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Test remote source API recommendation movie using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentRecommendationMovie)
        server.enqueue(successResponse)

        val response = service.getPopularMovies().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyMovie)
        assertEquals(dummyMovie.results?.size ?: -1, responseBody?.results?.size ?: 0,)
    }

    @Test
    fun `Test remote source API detail movie using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentDetailMovie)
        server.enqueue(successResponse)
        val dummyId = dummyDetailMovie.id ?: 459151

        val response = service.getDetailMovie(dummyId.toString()).execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyDetailMovie)
        assertEquals(dummyDetailMovie.genres?.size, responseBody?.genres?.size ?: 0)
    }

    @Test
    fun `Test remote source API now playing movie using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentRecommendationMovie)
        server.enqueue(successResponse)

        val response = service.getNowPlaying().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyMovie)
        assertEquals(dummyMovie.results?.size ?: -1, responseBody?.results?.size ?: 0)
    }

    @Test
    fun getNowPlaying() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getNowPlayingMovie()).thenReturn(dataSourceFactory)
        repository.getNowPlaying()

        val movieEntityList = Resource.success(PagedListUtil.mockPagedList(DummyGenerator.generateDummyMovies()))
        verify(local).getNowPlayingMovie()
        assertNotNull(movieEntityList)
        assertEquals(dummyRemoteMovie.size, movieEntityList.data?.size)
    }

    @Test
    fun getPopularMovies() {
        val movie = MutableLiveData<Resource<List<MovieItem>>>()
        val res = Resource.success(dummyMovieItem)
        movie.value = res

        `when`(remote.getPopularMovies(dummyIdMovie)).thenReturn(movie)
        val data = LiveDataTestUtil.getValue(repository.getPopularMovies(dummyIdMovie))
        verify(remote).getPopularMovies(dummyIdMovie)

        assertNotNull(data)
        assertEquals(movie.value?.data?.size, data.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val movie = MutableLiveData<MovieEntity>()
        val dummyMovieList = DummyGenerator.generateDummyMovies()
        val dummyMovie = dummyMovieList[0]
        movie.postValue(dummyMovie)
        val idMovie = dummyMovie.movieId

        `when`(local.getDetailMovieById(idMovie)).thenReturn(movie)
        repository.getDetailMovie(idMovie)

        val movieEntityList = Resource.success(PagedListUtil.mockPagedList(DummyGenerator.generateDummyMovies()))
        verify(local).getDetailMovieById(idMovie)
        assertNotNull(movieEntityList)
        assertEquals(dummyMovie, movieEntityList.data!![0]!!)
    }
}