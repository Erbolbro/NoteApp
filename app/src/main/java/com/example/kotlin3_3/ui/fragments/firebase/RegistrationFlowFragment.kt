package com.example.kotlin3_3.ui.fragments.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.FragmentRegistrationFlowBinding
import org.koin.android.ext.android.inject

class RegistrationFlowFragment : Fragment() {

    private var _binding: FragmentRegistrationFlowBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationFlowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNavigation(savedInstanceState)
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.sign_nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}