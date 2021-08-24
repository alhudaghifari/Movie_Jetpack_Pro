package com.alhudaghifari.moviegood.ui.favorite.movies

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.databinding.ActivityDetailFavoriteMoviesBinding
import com.alhudaghifari.moviegood.databinding.ContentDetailFavoriteBinding
import com.alhudaghifari.moviegood.ui.detailmovie.DetailMovieCallback
import com.alhudaghifari.moviegood.ui.detailmovie.DetailMovieViewModel
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFavoriteMoviesActivity : AppCompatActivity(), DetailMovieCallback {

    companion object {
        const val MOVIE_DATA = "movie_data"
    }

    private lateinit var binding : ContentDetailFavoriteBinding
    private val viewModel : DetailMovieViewModel by viewModels()
    private lateinit var movieEntity: MovieEntity
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingActivity = ActivityDetailFavoriteMoviesBinding.inflate(layoutInflater)
        binding = bindingActivity.detailContent
        setContentView(bindingActivity.root)

        setSupportActionBar(bindingActivity.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val extras = intent.extras
        if (extras != null) {
            val movieData = extras.getParcelable<MovieEntity>(MOVIE_DATA)
            if (movieData != null) {
                movieEntity = movieData
                observeMovieData(movieData.movieId)
                populateMovie(movieData)
            }
        }

        setFavoriteButtonListener()
    }

    override fun onClicked(movie: MovieEntity) {
        val intent = Intent(this, DetailFavoriteMoviesActivity::class.java)
        intent.putExtra(MOVIE_DATA, movie)
        startActivity(intent)
        finish()
    }

    private fun setFavoriteButtonListener() {
        binding.ibFavorite.setOnClickListener {
            viewModel.setFavorite(movieEntity, !isFav)
        }
    }

    private fun observeMovieData(id: String) {
        viewModel.getDetailMovie(id).observe(this, {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> showDetailLoading()
                    Status.SUCCESS -> {
                        showDetailAndHideLoading()
                        it.data?.let {
                            movieEntity = it
                            it.category.let {
                                binding.tvCategory.text = it
                            }
                            it.tagline.let { tagline ->
                                binding.tvTagline.text = tagline ?: "-"
                            }
                            it.isFavorite.let {
                                isFav = it
                                if (isFav) {
                                    binding.ibFavorite.setImageResource(R.drawable.ic_favorite_full_red)
                                } else {
                                    binding.ibFavorite.setImageResource(R.drawable.ic_favorite_border_red)
                                }
                            }
                        }
                    }
                    Status.ERROR -> {
                        showDetailAndHideLoading()
                    }
                }

            }
        })
    }

    private fun showDetailLoading() {
        with(binding) {
            progressBarDetail.visibility = View.VISIBLE
            tvCategoryTitle.visibility = View.GONE
            tvCategory.visibility = View.GONE
            tvTagline.visibility = View.GONE
            tvTaglineTitle.visibility = View.GONE
        }
    }

    private fun showDetailAndHideLoading() {
        with(binding) {
            progressBarDetail.visibility = View.GONE
            tvCategoryTitle.visibility = View.VISIBLE
            tvCategory.visibility = View.VISIBLE
            tvTagline.visibility = View.VISIBLE
            tvTaglineTitle.visibility = View.VISIBLE
        }
    }


    private fun populateMovie(data : MovieEntity) {
        with(binding) {
            val percentScore = data.score?.times(10) ?: 0
            val score = "${getString(R.string.score)} : ${percentScore.toInt()}%"
            val imgPath = "${ApiConstant.base_url_img}${data.imagePath}"

            tvTitleMovie.text = data.title
            tvReleased.text = data.released
            tvScore.text = score
            tvOverview.text = data.overview

            if (data.tagline.isNotEmpty()) {
                tvTagline.text = data.tagline
            }
            if (data.category.isNotEmpty()) {
                tvCategory.text = data.category
            }

            Glide.with(this@DetailFavoriteMoviesActivity)
                .load(imgPath)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(ivImageDetail)
        }
    }
}