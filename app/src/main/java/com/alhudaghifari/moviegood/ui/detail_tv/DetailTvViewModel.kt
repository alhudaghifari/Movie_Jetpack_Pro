package com.alhudaghifari.moviegood.ui.detail_tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel(){

    fun getDetailTv(idTv: String) : LiveData<Resource<TvDetailResponse>> = repository.getDetailTv(idTv)

    fun getRecommendationTv(currentIdTv: Int) : LiveData<Resource<List<TvItem>>> = repository.getPopularTv(currentIdTv)
}