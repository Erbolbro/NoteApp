package com.example.kotlin3_3.ui.fragments.firebase.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.data.repository.impl.AuthRepositoryImpl
import com.example.kotlin3_3.ui.fragments.intent.SignIntent
import com.example.kotlin3_3.utils.CoroutineDispatchers
import com.example.kotlin3_3.utils.state.AuthNoteState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel(
    private val authRepository: AuthRepositoryImpl, private val dispatcher: CoroutineDispatchers
) : ViewModel() {
    private val _signInState = MutableStateFlow<AuthNoteState>(AuthNoteState.Loading)
    val signInState: StateFlow<AuthNoteState> = _signInState
}