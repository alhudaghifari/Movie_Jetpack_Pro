package com.alhudaghifari.moviegood.ui.detailmovie

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.data.remote.model.MovieItem
import com.alhudaghifari.moviegood.databinding.ActivityDetailMovieBinding
import com.alhudaghifari.moviegood.databinding.ContentDetailBinding
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity(), DetailMovieCallback {

    companion object {
        const val MOVIE_DATA = "movie_data"
    }

    private lateinit var binding : ContentDetailBinding
    private val viewModel : DetailMovieViewModel by viewModels()
    private lateinit var movieAdapter : DetailMovieAdapter
    private lateinit var movieEntity: MovieEntity
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingActivity = ActivityDetailMovieBinding.inflate(layoutInflater)
        binding = bindingActivity.detailContent
        setContentView(bindingActivity.root)

        setSupportActionBar(bindingActivity.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        movieAdapter = DetailMovieAdapter(this)

        val extras = intent.extras
        if (extras != null) {
            val movieData = extras.getParcelable<MovieEntity>(MOVIE_DATA)
            if (movieData != null) {
                movieEntity = movieData
                observeMovieData(movieData.movieId)
                observeRecommendationData(movieData.movieId.toInt())
                populateMovie(movieData)
            }
        }

        setFavoriteButtonListener()

        with(binding.rvRecommendation) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailMovieActivity, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = movieAdapter
        }
    }

    override fun onClicked(movie: MovieEntity) {
        val intent = Intent(this, DetailMovieActivity::class.java)
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
        EspressoIdlingResource.increment()
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
                        EspressoIdlingResource.decrement()
                    }
                    Status.ERROR -> {
                        showDetailAndHideLoading()
                        EspressoIdlingResource.decrement()
                    }
                }

            }
        })
    }

    private fun observeRecommendationData(id: Int) {
        EspressoIdlingResource.increment()
        viewModel.getRecommendationMovie(id).observe(this, {
            it.let {
                when (it.status) {
                    Status.LOADING -> showRecLoading()
                    Status.SUCCESS -> {
                        showRecAndHideLoading()
                        hideNoDataRecommendationText()
                        movieAdapter.setRecommendationData(it.data)
                        EspressoIdlingResource.decrement()
                    }
                    Status.ERROR -> {
                        showRecAndHideLoading()
                        showNoDataRecommendationText()
                        EspressoIdlingResource.decrement()
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

    private fun showRecLoading() {
        with(binding) {
            progressBarRecommendation.visibility = View.VISIBLE
            rvRecommendation.visibility = View.GONE
        }
    }

    private fun showRecAndHideLoading() {
        with(binding) {
            progressBarRecommendation.visibility = View.GONE
            rvRecommendation.visibility = View.VISIBLE
        }
    }

    private fun showNoDataRecommendationText() {
        binding.tvNoRecommendation.visibility = View.VISIBLE
    }

    private fun hideNoDataRecommendationText() {
        binding.tvNoRecommendation.visibility = View.GONE
    }

    private fun populateMovie(data : MovieEntity) {
        with(binding) {
            val percentScore = data.score.times(10) ?: 0
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

            Glide.with(this@DetailMovieActivity)
                .load(imgPath)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(ivImageDetail)
        }
    }
}