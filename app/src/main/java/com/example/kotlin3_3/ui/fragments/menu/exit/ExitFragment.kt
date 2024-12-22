package com.example.kotlin3_3.ui.fragments.menu.exit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.databinding.FragmentExitBinding

class ExitFragment : Fragment() {

    private var _binding: FragmentExitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExitBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AlertDialog.Builder(requireActivity())
            .setTitle("Выход")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton("Да") { _, _ ->
                requireActivity().finishAffinity()
            }
            .setNegativeButton("Нет") { _, _ -> findNavController().navigateUp() }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}