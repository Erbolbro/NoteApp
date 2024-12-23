package com.example.kotlin3_3.ui.fragments.menu.dev

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.databinding.FragmentDevBinding

class DevFragment : Fragment() {
    private var _binding: FragmentDevBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDevBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goBack()
        goEmail()
        goTelegram()
        goLinkedin()
    }

    private fun goEmail() {
        binding.email.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.data = Uri.parse("mailto:erbolulanov@gmail.com")
            startActivity(intent)
        }
    }

    private fun goTelegram() {
        binding.telegram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(("https://t.me/Erbolbro"))
            startActivity(intent)
        }
    }

    private fun goLinkedin() {
        binding.linkedin.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.linkedin.com/in/erbol-ulanov-46ab3a324/")
            startActivity(intent)
        }
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