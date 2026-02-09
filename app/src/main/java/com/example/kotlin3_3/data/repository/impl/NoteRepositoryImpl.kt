package com.example.kotlin3_3.data.repository.impl

import com.example.kotlin3_3.data.local.room.dao.NoteDao
import com.example.kotlin3_3.data.local.room.entities.Note

class NoteRepositoryImpl(private val noteDao: NoteDao) {

    suspend fun addNotes(note: Note): Result<Unit> {
        return try {
            noteDao.addNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllNotes(): Result<List<Note>> {
        return try {
            Result.success(noteDao.getAllNotes())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateNote(note: Note): Result<Unit> {
        return try {
            noteDao.updateNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteNote(note: Note): Result<Unit> {
        return try {
            noteDao.deleteNote(note)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}