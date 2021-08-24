package com.alhudaghifari.moviegood.ui.favorite.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.utils.DummyGenerator
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
class FavoriteMoviesViewModelTest {

    private lateinit var viewModel: FavoriteMoviesViewModel
    private val dummyMovie = DummyGenerator.generateDummyMovies()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MovieRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var moviePagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMoviesViewModel(repository)
    }

    @Test
    fun getFavoriteMovies() {
        val movie = MutableLiveData<PagedList<MovieEntity>>()
        movie.value = moviePagedList
        `when`(movie.value?.size).thenReturn(12)

        `when`(repository.getFavoriteMovies()).thenReturn(movie)
        val movieData = viewModel.getFavoriteMovies().value
        verify(repository).getFavoriteMovies()

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(moviePagedList)

        assertNotNull(movieData)
        assertEquals(dummyMovie.size, movieData?.size)
    }
}