package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import com.alhudaghifari.moviegood.data.remote.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.data.remote.source.TvDataSource
import com.alhudaghifari.moviegood.utils.Resource
import javax.inject.Inject

class TvRepository @Inject constructor(
    private val dataSource: TvDataSource
    ) : TvDataInterface {
    override fun getOnTheAir(): LiveData<Resource<TvResponse>> = dataSource.getOnTheAir()

    override fun getPopularTv(currentIdMovie: Int): LiveData<Resource<List<TvItem>>> = dataSource.getPopularTv(currentIdMovie)

    override fun getDetailTv(idTv: String): LiveData<Resource<TvDetailResponse>> = dataSource.getDetailTv(idTv)
}