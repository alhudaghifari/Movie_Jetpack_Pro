package com.alhudaghifari.moviegood.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.databinding.ActivityMainBinding
import com.alhudaghifari.moviegood.ui.favorite.FavoriteActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_favorite) {
            startActivity(Intent(this, FavoriteActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}