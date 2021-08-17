package com.alhudaghifari.moviegood.ui.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.moviegood.databinding.FragmentMoviesBinding
import com.alhudaghifari.moviegood.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var fragmentAcademyBinding: FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        fragmentAcademyBinding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return fragmentAcademyBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            observeData()
        }
    }

    private fun observeData() {
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
                            val moviesAdapter = MoviesAdapter()
                            moviesAdapter.setMovies(it.results)
                            with(fragmentAcademyBinding.rvData) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                adapter = moviesAdapter
                            }
                        }
                    }
                    Status.ERROR -> {
                        hideLoading()
                        showNoData()
                    }
                }
            }
        })
    }

    private fun showLoading() {
        fragmentAcademyBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        fragmentAcademyBinding.progressBar.visibility = View.GONE
    }

    fun showNoData() {
        fragmentAcademyBinding.rvData.visibility = View.GONE
        fragmentAcademyBinding.tvNoData.visibility = View.VISIBLE
    }

    fun showDataList() {
        fragmentAcademyBinding.rvData.visibility = View.VISIBLE
        fragmentAcademyBinding.tvNoData.visibility = View.GONE
    }


}