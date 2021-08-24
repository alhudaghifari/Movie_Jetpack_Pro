package com.alhudaghifari.moviegood.ui.tvshows

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.TvEntity
import com.alhudaghifari.moviegood.databinding.ItemTvShowBinding
import com.alhudaghifari.moviegood.ui.detailtv.DetailTvActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class TvShowsAdapter(private val callback: TvShowCallback) :  PagedListAdapter<TvEntity, TvShowsAdapter.MyViewHolder>(DIFF_CALLBACK) {

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


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val muv = getItem(position)
        if (muv != null) {
            holder.bind(muv)
        }
    }

    inner class MyViewHolder(private val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvEntity) {
            with(binding) {
                val percentScore = tv.score?.times(10) ?: 0
                val score = "${itemView.context.getString(R.string.score)} : ${percentScore.toInt()}%"
                val imgPath = "${ApiConstant.base_url_img}${tv.imagePath}"

                tvItemTitle.text = tv.title
                tvItemReleased.text = tv.released
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