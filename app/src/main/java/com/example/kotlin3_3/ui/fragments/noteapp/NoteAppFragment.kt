package com.example.kotlin3_3.ui.fragments.noteapp

import NoteAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.App
import com.example.kotlin3_3.R
import com.example.kotlin3_3.databinding.FragmentNoteAppBinding

class NoteAppFragment : Fragment() {
    private var _binding: FragmentNoteAppBinding? = null
    private val biding: FragmentNoteAppBinding get() = _binding!!
    private val noteAdapter = NoteAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteAppBinding.inflate(layoutInflater)
        return biding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateToAddNote()
        initialize()
    }

    private fun navigateToAddNote() = with(biding) {
        btnNoteApp.setOnClickListener {
            findNavController().navigate(R.id.action_noteAppFragment_to_addNoteFragment)
        }
    }

    private fun initialize() = with(biding) {
        App.db?.let {
            it.noteDao().getAllNotes()
            noteAdapter.setNoteList(it.noteDao().getAllNotes())
        }
        biding.rvNotes.adapter = noteAdapter
    }
}