package com.alhudaghifari.moviegood.ui.favorite.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.entity.MovieEntity
import com.alhudaghifari.moviegood.databinding.ItemMoviesBinding
import com.alhudaghifari.moviegood.ui.detailmovie.DetailMovieActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavoriteMoviesAdapter : PagedListAdapter<MovieEntity, FavoriteMoviesAdapter.MoviesViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.movieId == newItem.movieId
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
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
        fun bind(movie: MovieEntity) {
            with(binding) {
                val percentScore = movie.score.times(10) ?: 0
                val score = "${itemView.context.getString(R.string.score)} : ${percentScore.toInt()}%"
                val imgPath = "${ApiConstant.base_url_img}${movie.imagePath}"

                tvItemTitle.text = movie.title
                tvItemReleased.text = movie.released
                tvScore.text = score
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailFavoriteMoviesActivity::class.java)
                    intent.putExtra(DetailFavoriteMoviesActivity.MOVIE_DATA, movie)
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