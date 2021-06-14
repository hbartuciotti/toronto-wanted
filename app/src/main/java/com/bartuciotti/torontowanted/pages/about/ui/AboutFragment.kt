package com.bartuciotti.torontowanted.pages.about.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bartuciotti.torontowanted.R
import com.bartuciotti.torontowanted.databinding.FragmentAboutBinding

/**
 * About Page
 */
class AboutFragment : Fragment() {


    //View Binding
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!


    /** Life Cycle */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setButtonClickEvents()
    }

    private fun setButtonClickEvents() {

        binding.btMore.setOnClickListener {
            val uri = Uri.parse(getString(R.string.toronto_police_website))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }

        binding.btRateUs.setOnClickListener {
            val uri = Uri.parse(getString(R.string.appstore_link))
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}