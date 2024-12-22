package com.example.kotlin3_3.utils.state

sealed class DeleteNoteState< out T> {
    data object Loading : DeleteNoteState<Nothing>()
    data class Error(val throwable: Throwable, val message: String) : DeleteNoteState<Nothing>()
    data class Success<T>(val data: T) : DeleteNoteState<T>()
}