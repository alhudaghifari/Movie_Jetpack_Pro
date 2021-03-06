package com.alhudaghifari.moviegood.ui.detailtv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel(){

    fun getDetailTv(idTv: String) : LiveData<Resource<TvEntity>> = repository.getDetailTv(idTv)

    fun getRecommendationTv(currentIdTv: Int) : LiveData<Resource<List<TvItem>>> = repository.getPopularTv(currentIdTv)

    fun setFavorite(tv: TvEntity, isFavorite: Boolean) = repository.setFavoriteTv(tv, isFavorite)
}