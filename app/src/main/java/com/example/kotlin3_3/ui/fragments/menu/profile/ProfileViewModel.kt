package com.example.kotlin3_3.ui.fragments.menu.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileViewModel : ViewModel() {

    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String> get() = _userEmail

    private val _createdDate = MutableLiveData<String>()
    val createdDate: LiveData<String> get() = _createdDate

    init {
        loadUserProfile()
    }

    private fun loadUserProfile() {
        val user = FirebaseAuth.getInstance().currentUser
        _userEmail.value = user?.email ?: "Не авторизован"

        val metadata = user?.metadata
        if (metadata != null) {
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            _createdDate.value = dateFormat.format(Date(metadata.creationTimestamp))
        } else {
            _createdDate.value = "Дата неизвестна"
        }
    }
}
