package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import com.alhudaghifari.moviegood.data.remote.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.utils.Resource

interface TvDataInterface {
    fun getOnTheAir() : LiveData<Resource<TvResponse>>

    fun getPopularTv(currentIdMovie: Int) : LiveData<Resource<List<TvItem>>>

    fun getDetailTv(idTv: String) : LiveData<Resource<TvDetailResponse>>
}