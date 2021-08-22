package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import com.alhudaghifari.moviegood.vo.Resource

interface TvDataInterface {
    fun getOnTheAir() : LiveData<Resource<TvResponse>>

    fun getPopularTv(currentIdMovie: Int) : LiveData<Resource<List<TvItem>>>

    fun getDetailTv(idTv: String) : LiveData<Resource<TvDetailResponse>>
}