package com.example.kotlin3_3

import android.app.Application
import androidx.room.Room
import com.example.kotlin3_3.data.local.room.AppDatabase

private const val DATABASE_NAME = "note_database"

class App : Application() {

    companion object {
        var db: AppDatabase? = null
    }

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            DATABASE_NAME
        ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
}