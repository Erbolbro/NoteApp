package com.example.kotlin3_3.ui.fragments.firebase.signIn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.databinding.FragmentSignInBinding
import com.example.kotlin3_3.ui.fragments.intent.SignIntent
import com.example.kotlin3_3.utils.state.AuthNoteState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SignInViewModel by viewModel()
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
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.signInState.collect { state ->
                when (state) {
                    is AuthNoteState.Error -> {
                        Log.e("SignInFragment", "Sign-in error: ${state.message}")
                        binding.et.error = state.message
                    }

                    AuthNoteState.Loading -> {
                        Log.d("SignInFragment", "Signing in...")
                    }

                    is AuthNoteState.Success -> {
                        Log.d("SignInFragment", "Sign-in successful")
                        preferencesHelper.isRegisterShow = true
                        findNavController().navigate(R.id.action_signInFragment_to_noteFlowFragment)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
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
            viewModel.processIntent(SignIntent.Login(password, email))
        }
        binding.tvGoToSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
