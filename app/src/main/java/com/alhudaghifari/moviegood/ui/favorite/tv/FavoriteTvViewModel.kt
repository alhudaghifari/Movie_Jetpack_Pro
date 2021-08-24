package com.alhudaghifari.moviegood.ui.favorite.tv

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteTvViewModel  @Inject constructor(private val repository: TvRepository)
    : ViewModel() {
    fun getFavoriteTv(): LiveData<PagedList<TvEntity>> = repository.getFavoriteTv()
}