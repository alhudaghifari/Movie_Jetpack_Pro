package com.alhudaghifari.moviegood.ui.detailtv

import com.alhudaghifari.moviegood.data.remote.model.TvItem

interface DetailTvCallback {
    fun onClicked(data: TvItem)
}