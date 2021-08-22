package com.alhudaghifari.moviegood.ui.tvshows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.moviegood.R
import com.alhudaghifari.moviegood.data.remote.TvItem
import com.alhudaghifari.moviegood.databinding.FragmentTvShowsBinding
import com.alhudaghifari.moviegood.vo.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment(), TvShowCallback {

    private lateinit var fragmentTvShowsBinding: FragmentTvShowsBinding
    private val viewModel: TvShowsViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return fragmentTvShowsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            observeData()
        }
    }

    private fun observeData() {
        viewModel.getTvShows().observe(viewLifecycleOwner, {
            it?.let {
                when(it.status) {
                    Status.LOADING -> {
                        showLoading()
                    }
                    Status.SUCCESS -> {
                        hideLoading()
                        it.data?.let {
                            showDataList()
                            val adapter = TvShowsAdapter(this)
                            adapter.setMovie(it.tvItems)
                            with(fragmentTvShowsBinding.rvTvShows) {
                                layoutManager = LinearLayoutManager(context)
                                setHasFixedSize(true)
                                this.adapter = adapter
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

    override fun onShareClicked(tv: TvItem) {
        if (activity != null) {
            val mimeType = "text/plain"
            ShareCompat.IntentBuilder(requireActivity())
                .setType(mimeType)
                .setChooserTitle("Bagikan aplikasi ini sekarang.")
                .setText(resources.getString(R.string.share_text, tv.name))
                .startChooser()
        }
    }


    private fun showLoading() {
        fragmentTvShowsBinding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        fragmentTvShowsBinding.progressBar.visibility = View.GONE
    }

    fun showNoData() {
        fragmentTvShowsBinding.rvTvShows.visibility = View.GONE
        fragmentTvShowsBinding.tvNoData.visibility = View.VISIBLE
    }

    fun showDataList() {
        fragmentTvShowsBinding.rvTvShows.visibility = View.VISIBLE
        fragmentTvShowsBinding.tvNoData.visibility = View.GONE
    }
}