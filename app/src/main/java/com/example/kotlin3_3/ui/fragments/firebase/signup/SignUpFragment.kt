package com.example.kotlin3_3.ui.fragments.firebase.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.FragmentSignUpBinding
import com.example.kotlin3_3.ui.fragments.intent.SignIntent
import com.example.kotlin3_3.utils.state.AuthNoteState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignUpViewModel by inject()
    private val auth: FirebaseAuth by inject()
    private val preferencesHelper: PreferencesHelper by inject()

    override fun onResume() {
        super.onResume()
        auth.currentUser?.reload()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListener()
//        goToSignIn()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signUpState.collect { state ->
                when (state) {
                    is AuthNoteState.Error -> {
                        Log.e(
                            "SignUpFragment",
                            "Sign-up error: ${state.message}"
                        )
                        binding.et.error = "такой почта уже существует"
                    }

                    AuthNoteState.Loading -> Log.d("SignUpFragment", "Signing up...")

                    is AuthNoteState.Success -> {
                        Log.d("SignUpFragment", "Sign-up successful")
                        preferencesHelper.isRegisterShow = true
                        Log.d(
                            "SignUpFragment",
                            "isShownRegister set to: ${preferencesHelper.isShownOnBoard}"
                        )
                            findNavController().navigate(R.id.action_signUpFragment_to_noteFlowFragment)
                    }

                    else -> {
                        Log.e("SignUpFragment", "Unknown state: $state")
                    }
                }
            }
        }
    }

    private fun setupListener() {
        binding.btnSignIn.setOnClickListener {
            val email = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (email.isEmpty() || !validateEmail(email)) {
                binding.et.error = "Введите корректный email"
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 6) {
                binding.etPassword.error = "Пароль должен быть не менее 6 символов"
                return@setOnClickListener
            }
            viewModel.processIntent(SignIntent.Register(password, email))


        }
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

//    private fun goToSignIn() {
//        binding.tvGoToSignIn.setOnClickListener {
//            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}