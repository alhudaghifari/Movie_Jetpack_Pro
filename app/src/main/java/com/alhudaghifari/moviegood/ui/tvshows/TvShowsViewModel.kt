package com.alhudaghifari.moviegood.ui.tvshows

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel() {

    fun getTvShows() : LiveData<Resource<TvResponse>> = repository.getOnTheAir()
}