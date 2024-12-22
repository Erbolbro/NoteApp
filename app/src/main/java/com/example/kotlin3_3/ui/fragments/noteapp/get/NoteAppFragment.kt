package com.example.kotlin3_3.ui.fragments.noteapp.get

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.FragmentNoteAppBinding
import com.example.kotlin3_3.ui.adapters.NoteAdapter
import com.example.kotlin3_3.ui.fragments.intent.NoteIntent
import com.example.kotlin3_3.utils.state.AddNoteState
import com.example.kotlin3_3.utils.state.DeleteNoteState
import org.koin.android.ext.android.inject

class NoteAppFragment : Fragment() {

    private var _binding: FragmentNoteAppBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteAppViewModel by inject()
    private lateinit var noteAdapter: NoteAdapter
    private var noteList = listOf<Note>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setupListeners()
        initViewModel()
        setupToolbar()
        binding.toolbar.setNavigationOnClickListener{
            findNavController().navigate(R.id.noteFlowFragment)
        }
    }

    private fun initAdapter() {
        noteAdapter = NoteAdapter { note ->
            viewModel.deleteNote(note)
        }
        binding.rvNotes.adapter = noteAdapter
    }

    private fun initViewModel() {
        observeNotes()
        observeDeleteNoteState()
        viewModel.getAllNotes()
        viewModel.precessIntent(NoteIntent.AllNotes(noteList))
    }

    private fun setupToolbar() {
        binding.toolbar.setOnClickListener {
            findNavController().navigate(R.id.noteFlowFragment)
        }
    }

    private fun observeNotes() {
        viewModel.notes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is AddNoteState.Error -> {
                    Log.e("NoteAppFragment", "Error loading notes: ${state.message}")
                }

                AddNoteState.Loading -> {
                    Log.d("NoteAppFragment", "Loading notes...")
                }

                is AddNoteState.Success -> {
                    noteList = state.data
                    noteAdapter.setNoteList(noteList)
                    updateItemCount()
                }
            }
        }
    }

    private fun observeDeleteNoteState() {
        viewModel.deleteNotes.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DeleteNoteState.Error -> {
                    Log.e("NoteAppFragment", "Error deleting note: ${state.message}")
                }

                DeleteNoteState.Loading -> {
                    Log.d("NoteAppFragment", "Deleting note...")
                }

                is DeleteNoteState.Success -> {
                    Log.d("NoteAppFragment", "Note deleted successfully.")
                }
            }
        }
    }

    private fun setupListeners() {
        binding.etSearhNote.addTextChangedListener { queryText ->
            val query = queryText?.toString().orEmpty()
            val filteredList = if (query.isEmpty()) noteList else noteList.filter { note ->
                note.title.contains(query, ignoreCase = true)
            }
            noteAdapter.updateSearchQuery(query)
            noteAdapter.setNoteList(filteredList)
            updateItemCount()
        }
        binding.btnNoteApp.setOnClickListener {
            findNavController().navigate(R.id.action_noteAppFragment_to_addNoteFragment)
        }
    }

    private fun updateItemCount() {
        val countText = SpannableString(noteAdapter.itemCount.toString()).apply {
            setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(binding.root.context, R.color.white)
                ),
                0, length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}