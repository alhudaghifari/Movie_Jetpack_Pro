package com.alhudaghifari.moviegood.ui.detail_tv

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.MoviesData
import com.alhudaghifari.moviegood.data.local.RecommendationData
import com.alhudaghifari.moviegood.data.remote.*
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class DetailTvViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel(){

    fun getDetailTv(idTv: String) : LiveData<Resource<TvDetailResponse>> = repository.getDetailTv(idTv)

    fun getRecommendationTv(currentIdTv: Int) : LiveData<Resource<List<TvItem>>> = repository.getPopularTv(currentIdTv)
}