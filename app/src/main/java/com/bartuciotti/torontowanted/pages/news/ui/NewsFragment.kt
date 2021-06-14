package com.bartuciotti.torontowanted.pages.news.ui

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartuciotti.torontowanted.databinding.FragmentNewsBinding
import com.bartuciotti.torontowanted.pages.news.data.News
import com.bartuciotti.torontowanted.pages.news.ui.adapter.NewsAdapter
import com.bartuciotti.torontowanted.pages.news.viewmodel.NewsViewModel
import com.bartuciotti.torontowanted.util.NetworkStateReceiver
import dagger.hilt.android.AndroidEntryPoint

/**
 * News Page.
 * */
@AndroidEntryPoint
class NewsFragment : Fragment(),
    NewsAdapter.ClickListener,
    NetworkStateReceiver.ConnectivityReceiverListener {

    private val newsViewModel: NewsViewModel by viewModels()

    private val newsListAdapter = NewsAdapter()
    private var pageLoaded = false

    //View Binding
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!


    /** Life Cycle */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlePageLoading()
        setRecyclerView()

        //Observe viewModel for data changes
        newsViewModel.getNews().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                updateRecyclerView(it)
                pageLoaded = true
                handlePageLoading()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        registerReceivers()
    }

    override fun onStop() {
        super.onStop()
        pageLoadingHandler.removeCallbacksAndMessages(null)
        unregisterReceivers()
    }


    /** Recycler View */
    private fun setRecyclerView() {
        binding.recyclerview.adapter = newsListAdapter
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        newsListAdapter.setListener(this)
    }

    private fun updateRecyclerView(news: List<News>) {
        newsListAdapter.updateData(news)
    }


    /** Broadcast Receivers */
    private var networkStateReceiver: NetworkStateReceiver? = NetworkStateReceiver(this)
    private fun registerReceivers() {
        activity?.application?.registerReceiver(
            networkStateReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun unregisterReceivers() {
        try {
            activity?.application?.unregisterReceiver(networkStateReceiver)
        } catch (exception: IllegalArgumentException) {
            Log.e(TAG, "Unable to unregister receivers" + exception.localizedMessage)
        }
    }


    /** Listeners */
    override fun onNewsClicked(news: News) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(news.link)))
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        handlePageLoading()
    }


    /** Util */
    private val pageLoadingHandler = Handler(Looper.getMainLooper())
    private fun handlePageLoading() {
        Log.d("InvestigationsFragment", "checkError")
        binding.pbLoading.visibility = View.VISIBLE
        binding.includeEmpty.container.visibility = View.GONE

        val showErrorLayout = Runnable {
            binding.pbLoading.visibility = View.GONE
            binding.includeEmpty.container.visibility = View.VISIBLE
        }

        if (!pageLoaded) { //Nothing to be displayed
            binding.pbLoading.visibility = View.VISIBLE
            pageLoadingHandler.postDelayed(showErrorLayout, 5000)
        } else { //There is data to be displayed
            pageLoadingHandler.removeCallbacksAndMessages(null) //Cancel handler
            binding.pbLoading.visibility = View.GONE
            binding.includeEmpty.container.visibility = View.GONE
            binding.tvTitle.visibility = View.VISIBLE
            binding.dividerTop.visibility = View.VISIBLE
        }
    }

    private val TAG = NewsFragment::class.simpleName
}