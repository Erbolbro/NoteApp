package com.example.kotlin3_3.ui.fragments.firebase.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.data.repository.impl.AuthRepositoryImpl
import com.example.kotlin3_3.ui.fragments.intent.SignIntent
import com.example.kotlin3_3.utils.CoroutineDispatchers
import com.example.kotlin3_3.utils.state.AuthNoteState
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepositoryImpl,
    private val dispatcher: CoroutineDispatchers
) : ViewModel() {

    private val _signInState = MutableStateFlow<AuthNoteState>(AuthNoteState.Loading)
    val signInState: StateFlow<AuthNoteState> = _signInState

    fun processIntent(intent: SignIntent) {
        when (intent) {
            is SignIntent.Login -> signIn(email = intent.email, password = intent.password)
            is SignIntent.Register -> {}
        }
    }

    private fun signIn(email: String, password: String) {
        viewModelScope.launch(dispatcher.io) {
            _signInState.value = AuthNoteState.Loading
            try {
                authRepository.signIn(email, password)
                _signInState.value = AuthNoteState.Success("Successfully signed in")
            } catch (e: FirebaseAuthInvalidUserException) {
                _signInState.value = AuthNoteState.Error("Пользователь не найден")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                _signInState.value = AuthNoteState.Error("Неверный пароль")
            } catch (e: Exception) {
                _signInState.value = AuthNoteState.Error(e.message ?: "Неизвестная ошибка")
                Log.e("SignInViewModel", "Error signing in: ${e.message}")
            }
        }
    }
}
