package com.example.kotlin3_3.ui.fragments.noteapp.get

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.FragmentNoteAppBinding
import com.example.kotlin3_3.ui.adapters.NoteAdapter
import com.example.kotlin3_3.ui.fragments.intent.NoteIntent
import com.example.kotlin3_3.data.preference.PreferencesHelper
import com.example.kotlin3_3.utils.state.AddNoteState
import com.example.kotlin3_3.utils.state.DeleteNoteState
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteAppFragment : Fragment() {

    private var _binding: FragmentNoteAppBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NoteAppViewModel by viewModel()
    private val preferencesHelper: PreferencesHelper by inject()
    private lateinit var noteAdapter: NoteAdapter
    private var noteList = listOf<Note>()
    private var isGridLayout = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isGridLayout = savedInstanceState?.getBoolean("IS_GRID_LAYOUT") ?: false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("IS_GRID_LAYOUT", isGridLayout)
    }

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

        val layoutManager = if (isGridLayout) {
            androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
        } else {
            androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        }
        binding.rvNotes.layoutManager = layoutManager
        initAdapter()
        setupListeners()
        initViewModel()
        setupToolbar()
        changeItem()
    }

    private fun changeItem() {
        binding.ivDird.setOnClickListener {
            isGridLayout = !isGridLayout
            val layoutManager = if (isGridLayout) {
                androidx.recyclerview.widget.GridLayoutManager(requireContext(), 2)
            } else {
                androidx.recyclerview.widget.LinearLayoutManager(requireContext())
            }
            binding.rvNotes.layoutManager = layoutManager
            binding.ivDird.setImageResource(
                if (isGridLayout) R.drawable.iv_shape else R.drawable.shape__9_
            )
        }
    }

    private fun initAdapter() {
        noteAdapter = NoteAdapter(
            onEditClick = { note -> navigateToEditNote(note) },
            onDeleteClick = { note -> showDeleteConfirmation(note) }
        )
        noteAdapter.updateTextSize(preferencesHelper.textSize)
        binding.rvNotes.post {
            binding.rvNotes.adapter = noteAdapter
        }
    }

    private fun showDeleteConfirmation(note: Note) {
        AlertDialog.Builder(requireContext())
            .setTitle("Удаление заметки")
            .setMessage("Вы уверены, что хотите удалить заметку \"${note.title}\"?")
            .setPositiveButton("Удалить") { _, _ ->
                viewModel.deleteNote(note)
            }
            .setNegativeButton("Отмена", null)
            .show()
    }

    private fun navigateToEditNote(note: Note) {
        val bundle = Bundle().apply {
            putInt("noteId", note.id)
            putString("noteTitle", note.title)
            putString("noteDescription", note.description)
            putString("noteDate", note.date)
            putString("noteTime", note.time)
            putString("noteColor", note.color)
        }
        findNavController().navigate(R.id.action_noteAppFragment_to_addNoteFragment, bundle)
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
                    noteAdapter.submitList(noteList.toList())
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
            noteAdapter.submitList(filteredList.toList())
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
