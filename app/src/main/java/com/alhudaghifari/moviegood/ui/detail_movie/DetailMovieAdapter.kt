package com.alhudaghifari.moviegood.ui.detail_movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.RecommendationData
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.databinding.ItemRecommendationBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class DetailMovieAdapter(private val callback: DetailMovieCallback) :
    RecyclerView.Adapter<DetailMovieAdapter.MyViewHolder>() {

    private val listRecommendation = ArrayList<MovieItem>()

    fun setRecommendationData(data: List<MovieItem>?) {
        if (data == null) return
        this.listRecommendation.clear()
        this.listRecommendation.addAll(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemRecommendationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listRecommendation[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = listRecommendation.size

    inner class MyViewHolder(private val binding: ItemRecommendationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieItem) {
            with(binding) {
                val imgPath = "${ApiConstant.base_url_img}${movie.posterPath}"
                val score = movie.voteAverage?.times(10.0) ?: 0
                val percentScore = "${score.toInt()}%"

                tvScoreRecommendation.text = percentScore
                tvTitle.text = movie.title
                tvReleased.text = movie.releaseDate
                itemView.setOnClickListener {
                    callback.onClicked(movie)
                }
                Glide.with(itemView.context)
                    .load(imgPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error)
                    )
                    .into(ivImage)

            }
        }
    }
}