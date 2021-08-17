package com.alhudaghifari.moviegood.ui.movies

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.api.ApiConstant
import com.alhudaghifari.moviegood.data.local.MoviesData
import com.alhudaghifari.moviegood.data.remote.MovieItem
import com.alhudaghifari.moviegood.databinding.ItemMoviesBinding
import com.alhudaghifari.moviegood.ui.detail_movie.DetailMovieActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>(){

    private var listMovies = ArrayList<MovieItem>()

    fun setMovies(movies: List<MovieItem>?) {
        if (movies == null)
            return
        this.listMovies.clear()
        this.listMovies.addAll(movies)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val itemBinding = ItemMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size

    class MoviesViewHolder(private val binding : ItemMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieItem) {
            with(binding) {
                val percentScore = movie.voteAverage?.times(10) ?: 0
                val score = "${itemView.context.getString(R.string.score)} : ${percentScore.toInt()}%"
                val imgPath = "${ApiConstant.base_url_img}${movie.posterPath}"

                tvItemTitle.text = movie.title
                tvItemReleased.text = movie.releaseDate
                tvScore.text = score
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailMovieActivity::class.java)
                    intent.putExtra(DetailMovieActivity.MOVIE_DATA, movie)
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