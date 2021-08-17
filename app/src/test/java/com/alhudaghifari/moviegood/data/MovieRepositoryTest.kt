package com.alhudaghifari.moviegood.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alhudaghifari.moviegood.api.MovieService
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class MovieRepositoryTest {

    private val service = mock(MovieService::class.java)
    private val repo = FakeMovieRepository(service)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun getNowPlaying() {

        repo.getNowPlaying()

    }

    @Test
    fun getPopularMovies() {
    }

    @Test
    fun getDetailMovie() {
    }
}