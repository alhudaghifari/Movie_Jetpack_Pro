package com.alhudaghifari.moviegood.ui.favorite.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.databinding.FragmentFavoriteMoviesBinding
import com.alhudaghifari.moviegood.databinding.FragmentMoviesBinding
import com.alhudaghifari.moviegood.ui.movies.MoviesAdapter
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMoviesFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteMoviesBinding
    private lateinit var moviesAdapter: FavoriteMoviesAdapter
    private val viewModel: FavoriteMoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            moviesAdapter = FavoriteMoviesAdapter()
            with(binding.rvData) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
            observeData()
        }
    }

    private fun observeData() {
        EspressoIdlingResource.increment()
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {
            if (it != null) {
                showDataList()
                moviesAdapter.submitList(it)
                EspressoIdlingResource.decrement()
            } else {
                showNoData()
                EspressoIdlingResource.decrement()
            }
        })
    }

    private fun showNoData() {
        binding.rvData.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    }

    private fun showDataList() {
        binding.rvData.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
    }
}