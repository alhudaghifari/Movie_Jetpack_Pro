package com.alhudaghifari.moviegood.ui.detail_tv

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvViewModelTest {

    private lateinit var viewModel: DetailTvViewModel
    private val contentRecommendationTv = MockResponseFileReader("tv_asset.json").content
    private val contentDetailTv = MockResponseFileReader("tv_detail_asset.json").content
    private lateinit var dummyTv: TvResponse
    private lateinit var dummyTvItem: List<TvItem>
    private lateinit var dummyDetailTv: TvDetailResponse
    private var dummyIdTv: Int = 0

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: TvRepository

    @Mock
    private lateinit var observerTv: Observer<Resource<List<TvItem>>>

    @Mock
    private lateinit var observerDetailTv: Observer<Resource<TvDetailResponse>>

    @Before
    fun setUp() {
        val gson = Gson()
        dummyTv = gson.fromJson(contentRecommendationTv, TvResponse::class.java)
        dummyTvItem = dummyTv.tvItems!!
        dummyIdTv = dummyTv.tvItems?.get(0)?.id ?: 95281
        dummyDetailTv = gson.fromJson(contentDetailTv, TvDetailResponse::class.java)

        viewModel = DetailTvViewModel(repository)
    }

    @Test
    fun `Tes getPopularTv view model and repository`() {
        val movie = MutableLiveData<Resource<List<TvItem>>>()
        val res = Resource.success(dummyTvItem)
        movie.value = res

        Mockito.`when`(repository.getPopularTv(dummyIdTv)).thenReturn(movie)
        val movieData = viewModel.getRecommendationTv(dummyIdTv).value
        verify(repository).getPopularTv(dummyIdTv)

        viewModel.getRecommendationTv(dummyIdTv).observeForever(observerTv)
        verify(observerTv).onChanged(res)

        assertNotNull(movieData)
        assertEquals(dummyTvItem.size, movieData?.data?.size ?: 0)
    }

    @Test
    fun `Tes getDetailTv view model and repository`() {
        val movie = MutableLiveData<Resource<TvDetailResponse>>()
        val res = Resource.success(dummyDetailTv)
        movie.value = res

        Mockito.`when`(repository.getDetailTv(dummyIdTv.toString())).thenReturn(movie)
        val movieData = viewModel.getDetailTv(dummyIdTv.toString()).value
        verify(repository).getDetailTv(dummyIdTv.toString())
        verify(repository, Mockito.never()).getOnTheAir()

        viewModel.getDetailTv(dummyIdTv.toString()).observeForever(observerDetailTv)
        verify(observerDetailTv).onChanged(res)

        assertNotNull(movieData)

        assertEquals(movieData?.data?.name, dummyDetailTv.name)
        assertEquals(movieData?.data?.genres?.size, dummyDetailTv.genres?.size ?: 1)
        assertEquals(movieData?.data?.tagline, dummyDetailTv.tagline)
    }
}