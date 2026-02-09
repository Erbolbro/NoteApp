package com.example.kotlin3_3.ui.fragments.noteapp.get

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.data.repository.impl.NoteRepositoryImpl
import com.example.kotlin3_3.ui.fragments.intent.NoteIntent
import com.example.kotlin3_3.utils.state.AddNoteState
import com.example.kotlin3_3.utils.state.DeleteNoteState
import kotlinx.coroutines.launch

class NoteAppViewModel(private val noteRepository: NoteRepositoryImpl) :
    ViewModel() {

    private val _notes = MutableLiveData<AddNoteState<List<Note>>>()
    val notes: LiveData<AddNoteState<List<Note>>> = _notes

    fun precessIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.AddNote -> {}
            is NoteIntent.UpdateNote -> {}
            is NoteIntent.DeleteNote -> {
                deleteNote(intent.note)
            }

            is NoteIntent.AllNotes -> {
                getAllNotes()
            }
        }
    }

    fun getAllNotes() {
        _notes.value = AddNoteState.Loading
        viewModelScope.launch {
            val result = noteRepository.getAllNotes()
            if (result.isSuccess) {
                _notes.value = AddNoteState.Success(result.getOrDefault(emptyList()))
            } else {
                _notes.value =
                    AddNoteState.Error(
                        result.exceptionOrNull()!!,
                        result.exceptionOrNull().toString()
                    )
                Log.e("tag", "getAllNotes не пришли ")
            }
        }
    }

    private val _deleteNotes = MutableLiveData<DeleteNoteState<Note>>()
    val deleteNotes: LiveData<DeleteNoteState<Note>> = _deleteNotes

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            val result = noteRepository.deleteNote(note)
            if (result.isSuccess) {
                getAllNotes()
            } else {
                _notes.value =
                    AddNoteState.Error(
                        result.exceptionOrNull()!!,
                        result.exceptionOrNull().toString()
                    )
            }
        }
    }
}