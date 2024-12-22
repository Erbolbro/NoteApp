package com.example.kotlin3_3.di

import com.example.kotlin3_3.ui.fragments.firebase.signIn.SignInViewModel
import com.example.kotlin3_3.ui.fragments.firebase.signup.SignUpViewModel
//import com.example.kotlin3_3.ui.fragments.noteapp.NoteFlowModel
import com.example.kotlin3_3.ui.fragments.noteapp.add.AddNoteViewModels
import com.example.kotlin3_3.ui.fragments.noteapp.get.NoteAppViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { AddNoteViewModels(get()) }
    viewModel { NoteAppViewModel(get()) }
    viewModel { SignUpViewModel(get(), get()) }
    viewModel { SignInViewModel(get(), get()) }
//    viewModel { NoteFlowModel() }
}