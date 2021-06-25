package com.bartuciotti.torontowanted.pages.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.databinding.FragmentDetailsMissingBinding
import com.bartuciotti.torontowanted.pages.investigations.data.Missing
import com.bartuciotti.torontowanted.util.AnalyticsHelper
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

/**
 * Details Page for Missing people.
 * This page receives the object to be displayed(Missing) via arguments (navArgs).
 * */
@AndroidEntryPoint
class DetailsMissingFragment : Fragment() {


    private val args by navArgs<DetailsMissingFragmentArgs>()

    //View Binding
    private var _binding: FragmentDetailsMissingBinding? = null
    private val binding get() = _binding!!


    /** Life Cycle */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsMissingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnalyticsHelper.pageLoaded(TAG)
        displayDetails(args.currentMissing)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    /** UI */
    private fun displayDetails(missing: Missing) {
        Glide.with(requireContext())
            .load(missing.image)
            .placeholder(R.drawable.placeholder)
            .error(R.drawable.placeholder)
            .into(binding.image)

        binding.tvName.text = missing.name
        binding.tvLocation.text = missing.location

        binding.tvSince.text = missing.since
        binding.tvEthnicity.text = missing.ethnicity
        binding.tvAge.text = missing.age
        binding.tvGender.text = missing.gender

        binding.tvDetails.text = missing.details
        binding.tvDescription.text = missing.description
    }

    private val TAG = DetailsMissingFragment::class.simpleName as String
}