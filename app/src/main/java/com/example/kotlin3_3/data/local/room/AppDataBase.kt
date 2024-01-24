package com.example.kotlin3_3.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.kotlin3_3.data.local.room.convertors.DateConvertors
import com.example.kotlin3_3.data.local.room.dao.NoteDao
import com.example.kotlin3_3.data.local.room.entities.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)

@TypeConverters(DateConvertors::class)

abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}