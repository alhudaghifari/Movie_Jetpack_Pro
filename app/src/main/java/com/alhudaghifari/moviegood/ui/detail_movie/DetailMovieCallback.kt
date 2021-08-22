package com.alhudaghifari.moviegood.ui.detail_movie

import com.alhudaghifari.moviegood.data.remote.model.MovieItem

interface DetailMovieCallback {
    fun onClicked(movie: MovieItem)
}