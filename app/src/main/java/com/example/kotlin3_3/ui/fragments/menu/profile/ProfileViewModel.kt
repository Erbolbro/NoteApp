package com.example.kotlin3_3.ui.fragments.menu.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    init {
        loadUserEmail()
    }
    private fun loadUserEmail() {
        val user = FirebaseAuth.getInstance().currentUser
        _userEmail.value = user?.email ?: "not found"
    }
}