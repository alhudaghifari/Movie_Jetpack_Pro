package com.alhudaghifari.moviegood.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.databinding.ActivityFavoriteBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : AppCompatActivity() {

    private val TAB_TITLES_FAVORITE = intArrayOf(R.string.movies,
        R.string.tv_shows)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val pagerAdapter = FavoriteAdapter(this)
        binding.viewPager2.adapter = pagerAdapter

        TabLayoutMediator(binding.tabs, binding.viewPager2) { tab, position ->
            tab.text = getString(TAB_TITLES_FAVORITE[position])
        }.attach()
    }
}