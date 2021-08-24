package com.alhudaghifari.moviegood.ui.favorite.tv

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.databinding.ItemMoviesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteTvAdapter  : PagedListAdapter<TvEntity, FavoriteTvAdapter.MoviesViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvEntity>() {
            override fun areItemsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem.tvId == newItem.tvId
            }

            override fun areContentsTheSame(oldItem: TvEntity, newItem: TvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val muv = getItem(position)
        if (muv != null) {
            holder.bind(muv)
        }
    }

    class MoviesViewHolder(private val binding : ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                val percentScore = tv.score.times(10) ?: 0
                val score = "${itemView.context.getString(R.string.score)} : ${percentScore.toInt()}%"
                val imgPath = "${ApiConstant.base_url_img}${tv.imagePath}"

                tvItemTitle.text = tv.title
                tvItemReleased.text = tv.released
                tvScore.text = score
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFavoriteTvActivity::class.java)
                    intent.putExtra(DetailFavoriteTvActivity.TV_DATA, tv)
                    itemView.context.startActivity(intent)
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