package com.example.kotlin3_3.ui.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.FragmentOnBoardPagingBinding
import org.koin.android.ext.android.inject

class OnBoardPagingFragment : Fragment() {

    private var _binding: FragmentOnBoardPagingBinding? = null
    private val binding get() = _binding!!
    private val preferencesHelper: PreferencesHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardPagingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        getStarted()
    }

    private fun initialize() = with(binding) {
        when (requireArguments().getInt(ARG_ONBOARD_PAGE_POSITION)) {
            0 -> {
                tvDescription.text = getString(R.string.very_convenient_functionality)
                animationView.setAnimation(R.raw.animation_first)
            }

            1 -> {
                tvDescription.text = getString(R.string.Fast_high_quality_product)
                animationView.setAnimation(R.raw.animation_second)
            }

            2 -> {
                tvDescription.text = getString(R.string.lots_of_functions_and_interesting_features)
                animationView.setAnimation(R.raw.animation_third)
                tvStart.text = getString(R.string.start_work)
            }
        }
    }

    private fun getStarted() = with(binding) {
        tvStart.setOnClickListener {
            preferencesHelper.isShownOnBoard = true
            findNavController().navigate(R.id.action_onBoardFragment_to_signUpFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ONBOARD_PAGE_POSITION = "onboarding_page_position"
    }
}