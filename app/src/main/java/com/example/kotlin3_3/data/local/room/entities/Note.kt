package com.example.kotlin3_3.data.local.room.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val title: String,
    val description: String, )