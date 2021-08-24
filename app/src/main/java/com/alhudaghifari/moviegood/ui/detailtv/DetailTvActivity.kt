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
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.databinding.ActivityDetailTvBinding
import com.alhudaghifari.moviegood.databinding.ContentDetailBinding
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
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
                observeTvData(tvData.tvId)
                observeRecomData(tvData.tvId.toInt())
                populateTv(tvData)
            }
        }

        with(binding.rvRecommendation) {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@DetailTvActivity, RecyclerView.HORIZONTAL, false)
            setHasFixedSize(true)
            this.adapter = tvAdapter
        }
    }

    override fun onClicked(data: TvItem) {
        val intent = Intent(this, DetailTvActivity::class.java)
        intent.putExtra(TV_DATA, data)
        startActivity(intent)
        finish()
    }

    private fun observeTvData(id: String) {
        EspressoIdlingResource.increment()
        viewModel.getDetailTv(id).observe(this, {
            it?.let {
                when (it.status) {
                    Status.LOADING -> showDetailLoading()
                    Status.SUCCESS -> {
                        showDetailAndHideLoading()
                        it.data?.category.let {
                            binding.tvCategory.text = it
                        }
                        it.data?.tagline.let { tagline ->
                            binding.tvTagline.text = tagline ?: "-"
                        }
                        EspressoIdlingResource.decrement()
                    }
                    Status.ERROR -> {
                        EspressoIdlingResource.decrement()
                        showDetailAndHideLoading()
                    }
                }
            }
        })
    }

    private fun observeRecomData(id: Int) {
        EspressoIdlingResource.increment()
        viewModel.getRecommendationTv(id).observe(this, {
            it.let {
                when (it.status) {
                    Status.LOADING -> showRecLoading()
                    Status.SUCCESS -> {
                        showRecAndHideLoading()
                        hideNoDataRecommendationText()
                        tvAdapter.setRecommendationData(it.data)
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

    private fun hideDetail() {
        with(binding) {
            progressBarDetail.visibility = View.GONE
            tvCategoryTitle.visibility = View.GONE
            tvCategory.visibility = View.GONE
            tvTagline.visibility = View.GONE
            tvTaglineTitle.visibility = View.GONE
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