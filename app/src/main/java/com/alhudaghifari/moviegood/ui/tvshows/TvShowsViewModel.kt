package com.alhudaghifari.moviegood.ui.tvshows

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alhudaghifari.moviegood.data.TvRepository
import com.alhudaghifari.moviegood.data.local.MoviesData
import com.alhudaghifari.moviegood.data.remote.MovieResponse
import com.alhudaghifari.moviegood.data.remote.TvResponse
import com.alhudaghifari.moviegood.utils.DummyGenerator
import com.alhudaghifari.moviegood.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(private val repository: TvRepository)
    : ViewModel() {

    fun getTvShows() : LiveData<Resource<TvResponse>> = repository.getOnTheAir()
}