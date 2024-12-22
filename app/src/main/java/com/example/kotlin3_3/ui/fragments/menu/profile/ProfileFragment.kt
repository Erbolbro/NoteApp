package com.example.kotlin3_3.ui.fragments.menu.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.databinding.FragmentProfileBinding
import org.koin.android.ext.android.inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProfileViewModel by inject()

//    private val getContent =
//        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
//            binding.ivCover.setImageURI(uri)
//        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setupObservers()

    }
//
//    private fun setupObservers() {
//        viewModel.userEmail.observe(viewLifecycleOwner) { email ->
//            binding.tvName.text = email
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}