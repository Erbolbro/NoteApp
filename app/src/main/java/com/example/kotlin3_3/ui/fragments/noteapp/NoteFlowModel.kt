package com.example.kotlin3_3.ui.fragments.noteapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlin3_3.ui.fragments.intent.NoteFlowIntent
import com.example.kotlin3_3.utils.state.NoteFLowState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//class NoteFlowModel : ViewModel() {

//    private val _state = MutableStateFlow(NoteFLowState())
//    val state: StateFlow<NoteFLowState> get() = _state
//
//    fun handleIntent(intent: NoteFlowIntent) {
//        viewModelScope.launch {
//            when (intent) {
//                NoteFlowIntent.OpenDrawer -> _state.value = _state.value.copy(isDrawerOpen = true)
//                NoteFlowIntent.CloseDrawer -> _state.value = _state.value.copy(isDrawerOpen = false)
//            }
//        }
//    }
//}