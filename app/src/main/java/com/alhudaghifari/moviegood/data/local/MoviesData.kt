package com.alhudaghifari.moviegood.data.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MoviesData(
    var moviesId: String,
    var title: String,
    var released: String,
    var category: String,
    var score: String,
    var overview: String,
    var director: String,
    var imagePath: String,
) : Parcelable