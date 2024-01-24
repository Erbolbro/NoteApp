package com.example.kotlin3_3.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.kotlin3_3.data.local.room.entities.Note
@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: Note)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotes(notes: List<Note>)

    @Update
    fun apdeteNote(note: Note)

    @Delete
    fun deleteNote(note: Note)

    @Query("DELETE FROM NOTE_TABLE")
    fun clear()
}
