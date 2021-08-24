package com.alhudaghifari.moviegood.ui.tvshows

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TvShowsViewModelTest {

    private lateinit var viewModel: TvShowsViewModel
    private val dummyTv = DummyGenerator.generateDummyTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<TvEntity>>>

    @Mock
    private lateinit var tvPagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = TvShowsViewModel(repository)
    }

    @Test
    fun `Test get tv shows view model and repository`() {
        val tv = MutableLiveData<Resource<PagedList<TvEntity>>>()
        val res = Resource.success(tvPagedList)
        `when`(res.data?.size).thenReturn(dummyTv.size)
        tv.value = res

        `when`(repository.getOnTheAir()).thenReturn(tv)
        val movieData = viewModel.getTvShows().value
        Mockito.verify(repository).getOnTheAir()

        viewModel.getTvShows().observeForever(observer)
        Mockito.verify(observer).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyTv.size, movieData?.data?.size)
    }
}