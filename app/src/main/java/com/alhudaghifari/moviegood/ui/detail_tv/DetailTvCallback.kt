package com.alhudaghifari.moviegood.ui.detail_tv

import com.alhudaghifari.moviegood.data.remote.model.TvItem

interface DetailTvCallback {
    fun onClicked(data: TvItem)
}