package com.example.kotlin3_3.ui.fragments.intent

sealed class NoteFlowIntent {
    data object OpenDrawer : NoteFlowIntent()
    data object CloseDrawer : NoteFlowIntent()
}