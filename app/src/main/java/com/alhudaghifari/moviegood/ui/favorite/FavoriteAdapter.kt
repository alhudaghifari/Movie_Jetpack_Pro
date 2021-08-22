package com.alhudaghifari.moviegood.ui.favorite

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.ui.favorite.movies.FavoriteMoviesFragment
import com.alhudaghifari.moviegood.ui.favorite.tv.FavoriteTvFragment

class FavoriteAdapter (fa: FragmentActivity) : FragmentStateAdapter(fa) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
        0 -> FavoriteMoviesFragment()
        1 -> FavoriteTvFragment()
        else -> Fragment()
    }
}
