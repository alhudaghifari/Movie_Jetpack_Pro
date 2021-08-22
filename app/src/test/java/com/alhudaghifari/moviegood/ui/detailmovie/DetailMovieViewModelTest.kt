package com.alhudaghifari.moviegood.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.model.MovieDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.vo.Resource
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.verify
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

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

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

        viewModel = DetailMovieViewModel(repository)
    }

    @Test
    fun `Test getRecommendationMovie view model and repository`() {
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
    fun `Test getDetailMovie view model and repository`() {
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