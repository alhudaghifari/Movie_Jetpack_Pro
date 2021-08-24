package com.alhudaghifari.moviegood.ui.favorite.tv

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.databinding.FragmentFavoriteMoviesBinding
import com.alhudaghifari.moviegood.databinding.FragmentFavoriteTvBinding
import com.alhudaghifari.moviegood.ui.favorite.movies.FavoriteMoviesAdapter
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteTvFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteTvBinding
    private lateinit var tvAdapter: FavoriteTvAdapter
    private val viewModel: FavoriteTvViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteTvBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            tvAdapter = FavoriteTvAdapter()
            with(binding.rvData) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvAdapter
            }
            observeData()
        }
    }

    private fun observeData() {
        EspressoIdlingResource.increment()
        viewModel.getFavoriteTv().observe(viewLifecycleOwner, {
            if (it != null) {
                showDataList()
                tvAdapter.submitList(it)
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