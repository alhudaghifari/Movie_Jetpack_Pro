package com.alhudaghifari.moviegood.ui.tvshows

import com.alhudaghifari.moviegood.data.remote.model.TvItem

interface TvShowCallback {
    fun onShareClicked(tv: TvItem)
}