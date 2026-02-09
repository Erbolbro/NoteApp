package com.example.kotlin3_3.ui.fragments.menu.settings

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.FragmentSettingsBinding
import org.koin.android.ext.android.inject

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val preferencesHelper: PreferencesHelper by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        setupDarkThemeSwitch()
        setupTextSizeSelector()
    }

    private fun setupDarkThemeSwitch() {
        binding.switchDarkTheme.isChecked = preferencesHelper.isDarkTheme

        binding.switchDarkTheme.setOnCheckedChangeListener { _, isChecked ->
            preferencesHelper.isDarkTheme = isChecked
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun setupTextSizeSelector() {
        val currentSize = preferencesHelper.textSize
        when (currentSize) {
            0 -> binding.rbSmall.isChecked = true
            1 -> binding.rbMedium.isChecked = true
            2 -> binding.rbLarge.isChecked = true
        }
        updatePreview(currentSize)

        binding.rgTextSize.setOnCheckedChangeListener { _, checkedId ->
            val size = when (checkedId) {
                R.id.rb_small -> 0
                R.id.rb_medium -> 1
                R.id.rb_large -> 2
                else -> 1
            }
            preferencesHelper.textSize = size
            updatePreview(size)
        }
    }

    private fun updatePreview(sizeIndex: Int) {
        val titleSizeSp = when (sizeIndex) {
            0 -> 14f
            1 -> 16f
            2 -> 20f
            else -> 16f
        }
        val textSizeSp = when (sizeIndex) {
            0 -> 12f
            1 -> 14f
            2 -> 18f
            else -> 14f
        }
        binding.tvPreviewTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSizeSp)
        binding.tvPreviewText.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
