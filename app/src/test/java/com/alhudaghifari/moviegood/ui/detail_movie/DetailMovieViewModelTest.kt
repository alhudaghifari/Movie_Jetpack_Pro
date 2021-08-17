package com.alhudaghifari.moviegood.ui.detail_movie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.api.MovieService
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.data.remote.MovieResponse
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private val server: MockWebServer = MockWebServer()
    private val MOCK_WEBSERVER_PORT = 8000

    private lateinit var service: MovieService
    private lateinit var viewModel: DetailMovieViewModel
    private val contentRecommendationMovie = MockResponseFileReader("movie_asset.json").content
    private val contentDetailMovie = MockResponseFileReader("movie_detail_asset.json").content
    private lateinit var dummyMovie: MovieResponse
    private lateinit var dummyMovieItem: List<MovieItem>
    private lateinit var dummyDetailMovie: MovieDetailResponse
    private var dummyIdMovie: Int = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observerMovie: Observer<Resource<List<MovieItem>>>

    @Mock
    private lateinit var observerDetailMovie: Observer<Resource<MovieDetailResponse>>

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
        viewModel = DetailMovieViewModel(repository)
    }

    @After
    fun shutdown() {
        server.shutdown()
    }

    @Test
    fun `Tes response recommendation movie using Mock Web Server`() {
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
    fun `Tes response detail movie using Mock Web Server`() {
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
    fun `Tes getRecommendationMovie view model and repository`() {
        val movie = MutableLiveData<Resource<List<MovieItem>>>()
        val res = Resource.success(dummyMovieItem)
        movie.value = res

        `when`(repository.getPopularMovies(dummyIdMovie)).thenReturn(movie)
        val movieData = viewModel.getRecommendationMovie(dummyIdMovie).value
        verify(repository).getPopularMovies(dummyIdMovie)

        viewModel.getRecommendationMovie(dummyIdMovie).observeForever(observerMovie)
        verify(observerMovie).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyMovieItem.size, movieData?.data?.size ?: 0)
    }

    @Test
    fun `Tes getDetailMovie view model and repository`() {
        val movie = MutableLiveData<Resource<MovieDetailResponse>>()
        val res = Resource.success(dummyDetailMovie)
        movie.value = res

        `when`(repository.getDetailMovie(dummyIdMovie.toString())).thenReturn(movie)
        val movieData = viewModel.getDetailMovie(dummyIdMovie.toString()).value
        verify(repository).getDetailMovie(dummyIdMovie.toString())
        verify(repository, never()).getPopularMovies(dummyIdMovie)

        viewModel.getDetailMovie(dummyIdMovie.toString()).observeForever(observerDetailMovie)
        verify(observerDetailMovie).onChanged(res)

        assertNotNull(movieData)

        assertEquals(movieData?.data?.title, dummyDetailMovie.title)
        assertEquals(movieData?.data?.genres?.size, dummyDetailMovie.genres?.size ?: 1)
        assertEquals(movieData?.data?.tagline, dummyDetailMovie.tagline)
    }
}