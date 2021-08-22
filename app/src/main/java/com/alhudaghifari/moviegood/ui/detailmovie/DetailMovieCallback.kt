package com.alhudaghifari.moviegood.ui.detailmovie

import com.alhudaghifari.moviegood.data.remote.model.MovieItem

interface DetailMovieCallback {
    fun onClicked(movie: MovieItem)
}