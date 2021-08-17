package com.alhudaghifari.moviegood.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.MovieResponse
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: MovieService
    private lateinit var viewModel: MoviesViewModel
    private val contentMovie = MockResponseFileReader("movie_asset.json").content
    private lateinit var dummyMovie: MovieResponse

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<Resource<MovieResponse>>

    @Before
    fun setUp() {
        val gson = Gson()
        dummyMovie = gson.fromJson(contentMovie, MovieResponse::class.java)

        server.start(MOCK_WEBSERVER_PORT)
        service = Retrofit.Builder()
            .baseUrl(server.url("/"))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
            .create(MovieService::class.java)
        viewModel = MoviesViewModel(repository)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Tes response now playing movie using Mock Web Server`() {
        val successResponse = MockResponse().setBody(contentMovie)
        server.enqueue(successResponse)

        val response = service.getNowPlaying().execute()
        val responseBody = response.body()

        server.takeRequest()

        assertNotNull(responseBody)
        assertEquals(responseBody, dummyMovie)
        assertEquals(dummyMovie.results?.size ?: -1, responseBody?.results?.size ?: 0)
    }

    @Test
    fun `Tes getNowPlaying view model and repository`() {
        val movie = MutableLiveData<Resource<MovieResponse>>()
        val res = Resource.success(dummyMovie)
        movie.value = res

        `when`(repository.getNowPlaying()).thenReturn(movie)
        val movieData = viewModel.getNowPlaying().value
        verify(repository).getNowPlaying()

        viewModel.getNowPlaying().observeForever(observer)
        verify(observer).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyMovie.results?.size ?: -1, movieData?.data?.results?.size ?: 0)
    }
}