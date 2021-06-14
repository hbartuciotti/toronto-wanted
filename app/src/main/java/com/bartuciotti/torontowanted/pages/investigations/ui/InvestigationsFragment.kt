package com.bartuciotti.torontowanted.pages.investigations.ui

import android.content.Context
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
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.databinding.FragmentInvestigationsBinding
import com.bartuciotti.torontowanted.pages.investigations.data.Announcement
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bartuciotti.torontowanted.pages.investigations.ui.adapter.InvestigationsParentAdapter
import com.bartuciotti.torontowanted.pages.investigations.ui.viewbinder.*
import com.bartuciotti.torontowanted.pages.investigations.viewmodel.InvestigationsViewModel
import com.bartuciotti.torontowanted.util.NetworkStateReceiver
import dagger.hilt.android.AndroidEntryPoint

/**
 * Investigations Page.
 * This page has a single recyclerview that can bind any kind of view and nest horizontal or vertical
 * recycler views while still recycling each view for better performance.
 * */
@AndroidEntryPoint
class InvestigationsFragment : Fragment(),
    AnnouncementBinder.ClickListener,
    CategoriesBinder.ClickListener,
    WantedListBinder.ClickListener,
    UnsolvedListBinder.ClickListener,
    MissingListBinder.ClickListener,
    NetworkStateReceiver.ConnectivityReceiverListener {


    private val investigationsViewModel: InvestigationsViewModel by viewModels()

    private val investigationsListAdapter = InvestigationsParentAdapter()
    private var pageLoaded = false

    //View Binding
    private var _binding: FragmentInvestigationsBinding? = null
    private val binding get() = _binding!!


    /** Life Cycle */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInvestigationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlePageLoading()
        setRecyclerView()

        //Observe viewModel for data changes
        investigationsViewModel.getInvestigationsPage().observe(viewLifecycleOwner, Observer {
            updateRecyclerViewData(it)
            pageLoaded = true
            handlePageLoading()
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


    /** RecyclerView */
    private fun setRecyclerView() {

        binding.recyclerview.adapter = investigationsListAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())

        //Click Listeners
        investigationsListAdapter.setAnnouncementListener(this)
        investigationsListAdapter.setCategoriesListener(this)
        investigationsListAdapter.setWantedListListener(this)
        investigationsListAdapter.setUnsolvedListListener(this)
        investigationsListAdapter.setMissingListListener(this)
    }

    private fun updateRecyclerViewData(newData: List<Pair<Int, InvestigationsBaseBinder>>) {
        investigationsListAdapter.updateData(newData)
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
    override fun onChipClicked(chipId: Int) {
        Log.d(TAG, "onChipClicked")
        when (chipId) {
            CategoriesBinder.CHIP_WANTED -> scrollTo(investigationsListAdapter.getWantedPosition())
            CategoriesBinder.CHIP_UNSOLVED -> scrollTo(investigationsListAdapter.getUnsolvedPosition())
            CategoriesBinder.CHIP_MISSING -> scrollTo(investigationsListAdapter.getMissingPosition())
        }
    }

    override fun onWantedClicked(wantedCase: WantedCase) {
        Log.d(TAG, "onWantedClicked")
        val action = InvestigationsFragmentDirections.actionFragmentInvestigationsToFragmentDetails(
            wantedCase,
            null
        )
        findNavController().navigate(action)
    }

    override fun onUnsolvedClicked(unsolvedCase: UnsolvedCase) {
        Log.d(TAG, "onUnsolvedClicked")
        val action = InvestigationsFragmentDirections.actionFragmentInvestigationsToFragmentDetails(
            null,
            unsolvedCase
        )
        findNavController().navigate(action)
    }

    override fun onMissingClicked(missing: Missing) {
        Log.d(TAG, "onMissingClicked")
        val action =
            InvestigationsFragmentDirections.actionFragmentInvestigationsToFragmentDetailsMissing(
                missing
            )
        findNavController().navigate(action)
    }

    override fun onAnnouncementClicked(announcement: Announcement) {
        Log.d(TAG, "onAnnouncementClicked")
        if (!announcement.link.isNullOrEmpty()) { // Open link if exists
            val uri = Uri.parse(announcement.link)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        handlePageLoading()
    }


    /** Util */
    private fun scrollTo(position: Int) {
        val smoothScroller = smoothScrollToPosition(position, requireContext())
        binding.recyclerview.layoutManager?.startSmoothScroll(smoothScroller)
    }

    private fun smoothScrollToPosition(position: Int, context: Context): LinearSmoothScroller {
        val smoothScroller = object : LinearSmoothScroller(context) {
            override fun getVerticalSnapPreference(): Int {
                return SNAP_TO_START
            }
        }
        smoothScroller.targetPosition = position
        return smoothScroller
    }

    private val pageLoadingHandler = Handler(Looper.getMainLooper())
    private fun handlePageLoading() {
        Log.d(TAG, "handlePageLoading")
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
        }
    }

    private val TAG = InvestigationsFragment::class.simpleName
}