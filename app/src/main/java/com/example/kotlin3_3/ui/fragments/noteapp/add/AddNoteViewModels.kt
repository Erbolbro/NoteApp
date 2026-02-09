package com.example.kotlin3_3.ui.fragments.noteapp.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.data.repository.impl.NoteRepositoryImpl
import com.example.kotlin3_3.ui.fragments.intent.NoteIntent
import com.example.kotlin3_3.utils.state.AddNoteState
import kotlinx.coroutines.launch

class AddNoteViewModels(private val noteRepository: NoteRepositoryImpl) :
    ViewModel() {

    private val _notes = MutableLiveData<AddNoteState<Note>>()
    val notes: LiveData<AddNoteState<Note>> = _notes

    fun precessIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.AddNote -> addNote(intent.note)
            is NoteIntent.UpdateNote -> updateNote(intent.note)
            is NoteIntent.DeleteNote -> {}
            is NoteIntent.AllNotes -> {}
        }
    }

    private fun addNote(note: Note) {
        _notes.value = AddNoteState.Loading
        viewModelScope.launch {
            val result = noteRepository.addNotes(note)
            if (result.isSuccess) {
                _notes.value = AddNoteState.Success(note)
            } else {
                _notes.value = AddNoteState.Error(
                    throwable = result.exceptionOrNull()!!,
                    result.exceptionOrNull().toString()
                )
            }
        }
    }

    private fun updateNote(note: Note) {
        _notes.value = AddNoteState.Loading
        viewModelScope.launch {
            val result = noteRepository.updateNote(note)
            if (result.isSuccess) {
                _notes.value = AddNoteState.Success(note)
            } else {
                _notes.value = AddNoteState.Error(
                    throwable = result.exceptionOrNull()!!,
                    result.exceptionOrNull().toString()
                )
            }
        }
    }
}
