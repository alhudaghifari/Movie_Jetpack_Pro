package com.alhudaghifari.moviegood.ui.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.remote.model.TvItem
import com.alhudaghifari.moviegood.databinding.ItemTvShowBinding
import com.alhudaghifari.moviegood.ui.detail_tv.DetailTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowsAdapter(private val callback: TvShowCallback) : RecyclerView.Adapter<TvShowsAdapter.MyViewHolder>() {

    private val listMovies = ArrayList<TvItem>()

    fun setMovie(tv: List<TvItem>?) {
        if (tv == null) return
        this.listMovies.clear()
        this.listMovies.addAll(tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    inner class MyViewHolder(private val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvItem) {
            with(binding) {
                val percentScore = tv.voteAverage?.times(10) ?: 0
                val score = "${itemView.context.getString(R.string.score)} : ${percentScore.toInt()}%"
                val imgPath = "${ApiConstant.base_url_img}${tv.posterPath}"

                tvItemTitle.text = tv.name
                tvItemReleased.text = tv.firstAirDate
                tvScore.text = score
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailTvActivity::class.java)
                    intent.putExtra(DetailTvActivity.TV_DATA, tv)
                    itemView.context.startActivity(intent)
                }
                imgShare.setOnClickListener {
                    callback.onShareClicked(tv)
                }
                Glide.with(itemView.context)
                    .load(imgPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgPoster)

            }
        }
    }
}