package com.example.kotlin3_3.ui.fragments.noteapp.add

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.kotlin3_3.data.local.room.convertors.DateConvertors
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.FragmentAddNoteBinding
import com.example.kotlin3_3.ui.fragments.intent.NoteIntent
import com.example.kotlin3_3.utils.state.AddNoteState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNoteViewModels by viewModel()
    private val handler = Handler(Looper.getMainLooper())
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dataFormatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
    private val convertor = DateConvertors()

    private var editNoteId: Int = -1
    private var isEditMode: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkEditMode()
        setDateTime()
        observeViewModel()
        addYellowNote()
        addBlackNote()
        addBurgundy()
        goBack()
    }

    private fun checkEditMode() {
        arguments?.let { args ->
            val noteId = args.getInt("noteId", -1)
            if (noteId != -1) {
                isEditMode = true
                editNoteId = noteId
                binding.someId.setText(args.getString("noteTitle", ""))
                binding.some.setText(args.getString("noteDescription", ""))
            }
        }
    }

    private fun setDateTime() = with(binding) {
        tvData.text = convertor.formatDateOnly(Date())
        handler.post(object : Runnable {
            override fun run() {
                tvTime.text = convertor.formatTimeOnly(Date())
                handler.postDelayed(this, 60000)
            }
        })
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) {
            when (it) {
                is AddNoteState.Error -> {
                    Log.e("AddNoteFragment", "Ошибка: Заметка не сохранена", it.throwable)
                }

                AddNoteState.Loading -> {
                    Log.d("AddNoteFragment", "Загрузка...")
                }

                is AddNoteState.Success -> {
                    findNavController().navigateUp()
                    Log.d("AddNoteFragment", "Заметка успешно сохранена")
                }
            }
        }
    }

    private fun saveOrUpdateNote(color: String) {
        val title = binding.someId.text.toString().trim()
        val description = binding.some.text.toString().trim()
        if (title.isEmpty() || description.isEmpty()) {
            Log.d("AddNoteFragment", "Поля не должны быть пустыми")
            return
        }
        val note = Note(
            id = if (isEditMode) editNoteId else 0,
            title = title,
            description = description,
            date = dataFormatter.format(Date()),
            time = timeFormatter.format(Date()),
            color = color
        )
        if (isEditMode) {
            viewModel.precessIntent(NoteIntent.UpdateNote(note))
        } else {
            viewModel.precessIntent(NoteIntent.AddNote(note))
        }
    }

    private fun addYellowNote() {
        binding.btnHome2.setOnClickListener {
            saveOrUpdateNote("#EBE4C9")
        }
    }

    private fun addBlackNote() {
        binding.btnBlack.setOnClickListener {
            saveOrUpdateNote("#191818")
        }
    }

    private fun addBurgundy() {
        binding.btnBurgundy.setOnClickListener {
            saveOrUpdateNote("#571818")
        }
    }

    private fun goBack() {
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
        _binding = null
    }
}
