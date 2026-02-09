package com.example.kotlin3_3.ui.fragments.firebase.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.data.repository.impl.AuthRepositoryImpl
import com.example.kotlin3_3.ui.fragments.intent.SignIntent
import com.example.kotlin3_3.utils.CoroutineDispatchers
import com.example.kotlin3_3.utils.state.AuthNoteState
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepositoryImpl,
    private val dispatcher: CoroutineDispatchers
) : ViewModel() {

    private val _signUpState = MutableStateFlow<AuthNoteState>(AuthNoteState.Loading)
    val signUpState: StateFlow<AuthNoteState> = _signUpState

    fun processIntent(intent: SignIntent) {
        when (intent) {
            is SignIntent.Register -> signUp(password = intent.password, email = intent.email)
            is SignIntent.Login -> {}
        }
    }

    private fun signUp(password: String, email: String) {
        viewModelScope.launch(dispatcher.io) {
            _signUpState.value = AuthNoteState.Loading
            try {
                authRepository.signUp(email, password)
                _signUpState.value = AuthNoteState.Success("Successfully signed up")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _signUpState.value = AuthNoteState.Error("Invalid email format")
            } catch (e: FirebaseAuthWeakPasswordException) {
                _signUpState.value = AuthNoteState.Error("Weak password")
            } catch (e: Exception) {
                _signUpState.value = AuthNoteState.Error(e.message ?: "Unknown error")
                Log.e("SignUpViewModel", "Error signing up: ${e.message}")
            }
        }
    }
}