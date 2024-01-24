package com.example.kotlin3_3.ui.fragments.noteapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.App
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.FragmentAddNoteBinding
class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNoteToRoom()
    }
    private fun addNoteToRoom() = with(binding) {
        btnHome2.setOnClickListener {
            val title = someId.text.toString().trim()
            val note = some.text.toString().trim()
            App.db?.let {
                Log.e("add", "not null")
                it.noteDao().addNote(Note(title = title, description = note))
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}