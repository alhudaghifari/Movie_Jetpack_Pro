package com.alhudaghifari.moviegood.ui.detailtv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.databinding.ActivityDetailTvBinding
import com.alhudaghifari.moviegood.databinding.ContentDetailBinding
import com.alhudaghifari.moviegood.vo.Status
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailTvActivity : AppCompatActivity(), DetailTvCallback {

    companion object {
        const val TV_DATA = "tv_data"
    }

    private lateinit var binding : ContentDetailBinding
    private val viewModel : DetailTvViewModel by viewModels()
    private lateinit var tvAdapter : DetailTvAdapter
    private lateinit var tvEntity: TvEntity
    private var isFav = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bindingActivity = ActivityDetailTvBinding.inflate(layoutInflater)
        binding = bindingActivity.detailContent
        setContentView(bindingActivity.root)

        setSupportActionBar(bindingActivity.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvAdapter = DetailTvAdapter(this)

        val extras = intent.extras
        if (extras != null) {
            val tvData = extras.getParcelable<TvEntity>(TV_DATA)
            if (tvData != null) {
                tvEntity = tvData
                observeTvData(tvData.tvId)
                observeRecomData(tvData.tvId.toInt())
                populateTv(tvData)
            }
        }

        setFavoriteButtonListener()

        with(binding.rvRecommendation) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailTvActivity, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = tvAdapter
        }
    }

    override fun onClicked(data: TvEntity) {
        val intent = Intent(this, DetailTvActivity::class.java)
        intent.putExtra(TV_DATA, data)
        startActivity(intent)
        finish()
    }

    private fun setFavoriteButtonListener() {
        binding.ibFavorite.setOnClickListener {
            viewModel.setFavorite(tvEntity, !isFav)
        }
    }

    private fun observeTvData(id: String) {
        viewModel.getDetailTv(id).observe(this, {
            it?.let {
                when (it.status) {
                    Status.LOADING -> showDetailLoading()
                    Status.SUCCESS -> {
                        showDetailAndHideLoading()
                        it.data?.let {
                            tvEntity = it
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

    private fun observeRecomData(id: Int) {
        viewModel.getRecommendationTv(id).observe(this, {
            it.let {
                when (it.status) {
                    Status.LOADING -> showRecLoading()
                    Status.SUCCESS -> {
                        showRecAndHideLoading()
                        hideNoDataRecommendationText()
                        tvAdapter.setRecommendationData(it.data)
                    }
                    Status.ERROR -> {
                        showRecAndHideLoading()
                        showNoDataRecommendationText()
                    }
                }
            }
        })
    }

    private fun populateTv(data : TvEntity) {
        with(binding) {
            val percentScore = data.score.times(10) ?: 0
            val score = "${getString(R.string.score)} : ${percentScore.toInt()}%"
            val imgPath = "${ApiConstant.base_url_img}${data.imagePath}"

            tvTitleMovie.text = data.title
            tvReleased.text = data.released
            tvScore.text = score
            tvOverview.text = data.overview
            Glide.with(this@DetailTvActivity)
                .load(imgPath)
                .transform(RoundedCorners(20))
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(ivImageDetail)
        }
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
}