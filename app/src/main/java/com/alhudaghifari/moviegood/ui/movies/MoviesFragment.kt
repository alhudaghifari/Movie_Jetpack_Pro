package com.alhudaghifari.moviegood.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.moviegood.databinding.FragmentMoviesBinding
import com.alhudaghifari.moviegood.utils.EspressoIdlingResource
import com.alhudaghifari.moviegood.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAcademyBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            moviesAdapter = MoviesAdapter()
            with(fragmentAcademyBinding.rvData) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = moviesAdapter
            }
            observeData()
        }
    }

    private fun observeData() {
        EspressoIdlingResource.increment()
        viewModel.getNowPlaying().observe(viewLifecycleOwner, {
            if (it != null) {
                when(it.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        it.data?.let {
                            showDataList()
                            moviesAdapter.submitList(it)
                            EspressoIdlingResource.decrement()
                        }
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showNoData()
                        EspressoIdlingResource.decrement()
                    }
                }
            }
        })
    }

    private fun showLoading() {
        fragmentAcademyBinding.progressBar.visibility = View.VISIBLE
        fragmentAcademyBinding.rvData.visibility = View.GONE
    }

    private fun hideLoading() {
        fragmentAcademyBinding.progressBar.visibility = View.GONE
        fragmentAcademyBinding.rvData.visibility = View.VISIBLE
    }

    private fun showNoData() {
        fragmentAcademyBinding.rvData.visibility = View.GONE
        fragmentAcademyBinding.tvNoData.visibility = View.VISIBLE
    }

    private fun showDataList() {
        fragmentAcademyBinding.rvData.visibility = View.VISIBLE
        fragmentAcademyBinding.tvNoData.visibility = View.GONE
    }


}