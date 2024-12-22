package com.example.kotlin3_3.utils

sealed class UIState<out T> {
    data object Loading : UIState<Nothing>()
    data class Error(val throwable: Throwable, val message: String) : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()

}