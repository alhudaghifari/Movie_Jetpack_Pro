package com.alhudaghifari.moviegood.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.utils.MockResponseFileReader
import com.alhudaghifari.moviegood.vo.Resource
import com.google.gson.Gson
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

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

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
        viewModel = MoviesViewModel(repository)
    }

    @Test
    fun `Test getNowPlaying view model and repository`() {
//        val movie = MutableLiveData<Resource<MovieResponse>>()
//        val res = Resource.success(dummyMovie)
//        movie.value = res
//
//        `when`(repository.getNowPlaying()).thenReturn(movie)
//        val movieData = viewModel.getNowPlaying().value
//        verify(repository).getNowPlaying()
//
//        viewModel.getNowPlaying().observeForever(observer)
//        verify(observer).onChanged(res)
//
//        assertNotNull(movieData)
//        assertEquals(dummyMovie.results?.size ?: -1, movieData?.data?.results?.size ?: 0)
    }
}