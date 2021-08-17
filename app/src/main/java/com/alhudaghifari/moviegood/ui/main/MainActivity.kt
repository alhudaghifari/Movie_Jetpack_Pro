package com.alhudaghifari.moviegood.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alhudaghifari.moviegood.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityBinding.root)
        val pagerAdapter = MainAdapter(this,supportFragmentManager)
        activityBinding.viewPager.adapter = pagerAdapter
        activityBinding.tabs.setupWithViewPager(activityBinding.viewPager)
        supportActionBar?.elevation = 0f
    }
}