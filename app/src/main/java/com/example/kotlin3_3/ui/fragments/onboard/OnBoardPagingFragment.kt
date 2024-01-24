package com.example.kotlin3_3.ui.fragments.onboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.databinding.FragmentOnBoardPagingBinding
import com.example.kotlin3_3.ui.preference.PreferencesHelper

class OnBoardPagingFragment : Fragment() {

    private var _binding: FragmentOnBoardPagingBinding? = null
    private val preferencesHelper:PreferencesHelper by lazy {
        PreferencesHelper(context?:requireContext())
    }
    private val binding get() = _binding!!

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
                tvDescription.text = "Очень удобный функционал"
                animationView.setAnimation(R.raw.animation_first)
            }

            1 -> {
                tvDescription.text = "Быстрый, качественный продукт"
                animationView.setAnimation(R.raw.animation_second)
            }

            2 -> {
                tvDescription.text = "Куча функций и интересных фишек"
                animationView.setAnimation(R.raw.animation_third)
                tvStart.text = "Начать работу"
            }
        }
    }

    private fun getStarted() = with(binding) {
        preferencesHelper.isShownOnBoard = true
        tvStart.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardFragment_to_noteAppFragment)

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