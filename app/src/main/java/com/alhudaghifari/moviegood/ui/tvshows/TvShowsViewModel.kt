package com.alhudaghifari.moviegood.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.data.remote.model.TvResponse
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel() {

    fun getTvShows() : LiveData<Resource<PagedList<TvEntity>>> = repository.getOnTheAir()
}