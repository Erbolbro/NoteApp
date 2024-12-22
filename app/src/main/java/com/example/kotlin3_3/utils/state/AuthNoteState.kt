package com.example.kotlin3_3.utils.state

sealed class AuthNoteState {
    data object Loading : AuthNoteState()
    data class Error(val message: String) : AuthNoteState()
    data class Success(val message: String) : AuthNoteState()
}