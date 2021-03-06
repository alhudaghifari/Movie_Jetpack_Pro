package com.alhudaghifari.moviegood.ui.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.alhudaghifari.moviegood.data.MovieRepository
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.vo.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MovieRepository)
    : ViewModel() {

    fun getNowPlaying() :  LiveData<Resource<PagedList<MovieEntity>>>  = repository.getNowPlaying()

}