package com.alhudaghifari.moviegood.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "tv_table")
data class TvEntity(
    var tvId: String,
    var title: String,
    var released: String,
    var category: String,
    var score: String,
    var overview: String,
    var imagePath: String,
    var tagline: String,
) : Parcelable