package com.alhudaghifari.moviegood.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movie_table")
data class MovieEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "movieId")
    var movieId: String,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "released")
    var released: String,

    @ColumnInfo(name = "category")
    var category: String,

    @ColumnInfo(name = "score")
    var score: Double,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "imagePath")
    var imagePath: String,

    @ColumnInfo(name = "tagline")
    var tagline: String,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean = false,
) : Parcelable