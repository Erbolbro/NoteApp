package com.example.kotlin3_3.di

import com.example.kotlin3_3.data.repository.impl.AuthRepositoryImpl
import com.example.kotlin3_3.data.repository.impl.NoteRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single { NoteRepositoryImpl(get()) }
    single { AuthRepositoryImpl(get()) }
}