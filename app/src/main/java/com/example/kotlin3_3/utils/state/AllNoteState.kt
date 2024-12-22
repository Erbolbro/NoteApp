package com.example.kotlin3_3.utils.state

 sealed class AllNoteState< out T> {
     data object Loading : AllNoteState<Nothing>()
     data class Error(val throwable: Throwable, val message: String) : AllNoteState<Nothing>()
     data class Success<T>(val data: T) : AllNoteState<T>()
 }
