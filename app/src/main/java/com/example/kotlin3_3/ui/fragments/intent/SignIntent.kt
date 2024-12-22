package com.example.kotlin3_3.ui.fragments.intent

sealed class SignIntent {
    data class Register(val password: String, val email: String) : SignIntent()
    data class Login(val password: String, val email: String) : SignIntent()
}