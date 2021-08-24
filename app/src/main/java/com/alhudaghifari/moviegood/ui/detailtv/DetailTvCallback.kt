package com.alhudaghifari.moviegood.ui.detailtv

import com.alhudaghifari.moviegood.data.local.entity.TvEntity

interface DetailTvCallback {
    fun onClicked(data: TvEntity)
}