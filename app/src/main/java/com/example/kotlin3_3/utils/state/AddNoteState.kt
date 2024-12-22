package com.example.kotlin3_3.utils.state

sealed class AddNoteState<out T> {
    data object Loading : AddNoteState<Nothing>()
    data class Error(val throwable: Throwable, val message: String) : AddNoteState<Nothing>()
    data class Success<T>(val data: T) : AddNoteState<T>()
}