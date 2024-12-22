package com.example.kotlin3_3.data.repository.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private var auth: FirebaseAuth = FirebaseAuth.getInstance(),
) {
    suspend fun signUp(email: String, password: String) {
        try {
            auth.createUserWithEmailAndPassword(email, password).await()
        } catch (e: Exception) {
            Log.e("tag", "данные не пришли")
            throw Exception(e.message)
        }
    }

    suspend fun signIn(email: String, password: String) {
        try {
            auth.signInWithEmailAndPassword(email, password).await()
        } catch (e: FirebaseAuthInvalidUserException) {
            throw Exception("user not found")
        } catch (e: FirebaseAuthInvalidCredentialsException) {
            throw Exception("is not password")
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "error sign: ${e.message}")
            throw Exception("unknown error: ${e.message}")
        }
    }
}