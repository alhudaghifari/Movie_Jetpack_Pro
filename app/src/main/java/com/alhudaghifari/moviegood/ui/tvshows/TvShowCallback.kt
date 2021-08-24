package com.alhudaghifari.moviegood.ui.tvshows

import com.alhudaghifari.moviegood.data.local.entity.TvEntity

interface TvShowCallback {
    fun onShareClicked(tv: TvEntity)
}