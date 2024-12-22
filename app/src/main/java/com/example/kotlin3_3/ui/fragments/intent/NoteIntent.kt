package com.example.kotlin3_3.ui.fragments.intent

import com.example.kotlin3_3.data.local.room.entities.Note

sealed class NoteIntent {
    data class AddNote(val note: Note) : NoteIntent()
    data class DeleteNote(val note: Note) : NoteIntent()
    data class AllNotes(val notes: List<Note>) : NoteIntent()
}