package com.alhudaghifari.moviegood.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.remote.model.MovieResponse
import com.alhudaghifari.moviegood.data.remote.model.TvDetailResponse
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import com.alhudaghifari.moviegood.vo.Resource

interface TvDataInterface {
    fun getOnTheAir() : LiveData<Resource<PagedList<TvEntity>>>
    fun getPopularTv(currentId: Int) : LiveData<Resource<List<TvItem>>>
    fun getDetailTv(idTv: String) : LiveData<Resource<TvEntity>>
    fun getFavoriteTv() : LiveData<PagedList<TvEntity>>
    fun setFavoriteTv(tv: TvEntity, isFavorite: Boolean)
}