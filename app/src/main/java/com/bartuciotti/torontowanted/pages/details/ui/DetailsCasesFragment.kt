package com.bartuciotti.torontowanted.pages.details.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.databinding.FragmentDetailsInvestigationBinding
import com.bartuciotti.torontowanted.pages.details.ui.adapter.VictimsSuspectsAdapter
import com.bartuciotti.torontowanted.pages.details.viewmodel.DetailsViewModel
import com.bartuciotti.torontowanted.pages.investigations.data.UnsolvedCase
import com.bartuciotti.torontowanted.pages.investigations.data.WantedCase
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Details Page for Wanted criminals and Victims from unsolved cases.
 * This page receives the object to be displayed(WantedCase, UnsolvedCase) via arguments (navArgs).
 * */
@AndroidEntryPoint
class DetailsCasesFragment : Fragment() {


    private val args by navArgs<DetailsCasesFragmentArgs>()
    private val detailsViewModel: DetailsViewModel by viewModels()

    private val victimsOrSuspectsListAdapter = VictimsSuspectsAdapter()

    //View Binding
    private var _binding: FragmentDetailsInvestigationBinding? = null
    private val binding get() = _binding!!


    /** Life Cycle */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsInvestigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
        displayDetails()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    /** UI */
    private fun setRecyclerView() {
        binding.recyclerview.adapter = victimsOrSuspectsListAdapter
        binding.recyclerview.setHasFixedSize(true)
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun displayDetails() {
        when {
            args.currentWantedCase != null -> bindWantedCaseData(args.currentWantedCase!!)

            args.currentUnsolvedCase != null -> bindUnsolvedCaseData(args.currentUnsolvedCase!!)

            else -> Log.e(TAG, "No data to be displayed")
        }
    }

    private fun bindWantedCaseData(wantedCase: WantedCase) {
        binding.vDividerVictimDetails.visibility = View.GONE
        binding.victimInfoContainer.visibility = View.GONE

        Glide.with(requireContext())
            .load(wantedCase.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.image)

        binding.tvName.text = wantedCase.name
        binding.tvCharge.text = wantedCase.charge
        binding.tvHomicide.text = wantedCase.subtitle
        binding.tvCase.text = wantedCase.caseName
        binding.tvDetails.text = wantedCase.details
        binding.listLabel.text = this.resources.getString(R.string.victims_label)

        if (wantedCase.video != null)
            binding.btVideo.visibility = View.VISIBLE
        else binding.btVideo.visibility = View.GONE

        bindVictimsList(wantedCase.caseId)
    }

    private fun bindUnsolvedCaseData(unsolvedCase: UnsolvedCase) {
        Glide.with(requireContext())
            .load(unsolvedCase.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.image)

        binding.tvName.text = unsolvedCase.name
        binding.tvCharge.visibility = View.GONE
        binding.tvHomicide.text = unsolvedCase.subtitle
        binding.tvCase.text = unsolvedCase.caseName
        binding.tvAge.text = unsolvedCase.age
        binding.tvGender.text = unsolvedCase.gender
        binding.tvDivision.text = unsolvedCase.division
        binding.tvDate.text = unsolvedCase.date
        binding.tvDetails.text = unsolvedCase.details
        binding.btVideo.visibility = View.GONE
        binding.listLabel.text = this.resources.getString(R.string.suspects_label)

        bindSuspectsList(unsolvedCase.caseId)
    }

    private fun bindVictimsList(caseId: Int) {
        lifecycleScope.launch {
            val victims = detailsViewModel.getVictimsForCase(caseId)
            if (victims.isNotEmpty())
                victimsOrSuspectsListAdapter.updateVictimsList(victims)
            else {
                binding.vDividerVictims.visibility = View.GONE
                binding.llVictimsSuspectsContainer.visibility = View.GONE
            }
        }
    }

    private fun bindSuspectsList(caseId: Int) {
        lifecycleScope.launch {
            val suspects = detailsViewModel.getSuspectsForCase(caseId)
            if (suspects.isNotEmpty())
                victimsOrSuspectsListAdapter.updateSuspectsList(suspects)
            else {
                binding.vDividerVictims.visibility = View.GONE
                binding.llVictimsSuspectsContainer.visibility = View.GONE
            }
        }
    }

    private val TAG = DetailsCasesFragment::class.simpleName
}