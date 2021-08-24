package com.alhudaghifari.moviegood.ui.favorite.tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.utils.DummyGenerator
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
class FavoriteTvViewModelTest {
    private lateinit var viewModel: FavoriteTvViewModel
    private val dummyTv = DummyGenerator.generateDummyTvShows()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvEntity>>

    @Mock
    private lateinit var tvPagedList: PagedList<TvEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvViewModel(repository)
    }

    @Test
    fun getFavoriteTv() {
        val tv = MutableLiveData<PagedList<TvEntity>>()
        tv.value = tvPagedList
        Mockito.`when`(tv.value?.size).thenReturn(12)

        Mockito.`when`(repository.getFavoriteTv()).thenReturn(tv)
        val tvData = viewModel.getFavoriteTv().value
        Mockito.verify(repository).getFavoriteTv()

        viewModel.getFavoriteTv().observeForever(observer)
        Mockito.verify(observer).onChanged(tvPagedList)

        assertNotNull(tvData)
        assertEquals(dummyTv.size, tvData?.size)
    }
}