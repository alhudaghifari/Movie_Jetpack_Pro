package com.alhudaghifari.moviegood.ui.detailmovie

import com.alhudaghifari.moviegood.data.local.entity.MovieEntity

interface DetailMovieCallback {
    fun onClicked(movie: MovieEntity)
}