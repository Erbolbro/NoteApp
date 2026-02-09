package com.example.kotlin3_3.di

import androidx.room.Room
import com.example.kotlin3_3.data.local.room.AppDatabase
import com.example.kotlin3_3.data.local.room.Migrations
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.utils.CoroutineDispatchers
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "note_database"
        )
            .addMigrations(*Migrations.ALL_MIGRATIONS)
            .build()
    }
    single { get<AppDatabase>().noteDao() }
    single { PreferencesHelper(get()) }
    single { FirebaseAuth.getInstance() }

    single {
        CoroutineDispatchers(
            io = Dispatchers.IO,
            default = Dispatchers.Default,
            unconfined = Dispatchers.Main
        )
    }

}