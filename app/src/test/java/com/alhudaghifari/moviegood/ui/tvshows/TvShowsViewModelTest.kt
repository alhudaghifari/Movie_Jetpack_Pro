package com.alhudaghifari.moviegood.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel
    private val contentMovie = MockResponseFileReader("tv_asset.json").content
    private lateinit var dummyTv: TvResponse

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<Resource<TvResponse>>

    @Before
    fun setUp() {
        val gson = Gson()
        dummyTv = gson.fromJson(contentMovie, TvResponse::class.java)
        viewModel = TvShowsViewModel(repository)
    }

    @Test
    fun `Test get tv shows view model and repository`() {
        val tv = MutableLiveData<Resource<TvResponse>>()
        val res = Resource.success(dummyTv)
        tv.value = res

        Mockito.`when`(repository.getOnTheAir()).thenReturn(tv)
        val movieData = viewModel.getTvShows().value
        Mockito.verify(repository).getOnTheAir()

        viewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyTv.tvItems?.size ?: -1, movieData?.data?.tvItems?.size ?: 0)
    }
}