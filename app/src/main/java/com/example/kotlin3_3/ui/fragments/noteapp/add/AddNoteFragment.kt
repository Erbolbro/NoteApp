package com.example.kotlin3_3.ui.fragments.noteapp.add

import android.content.Context
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
import org.koin.android.ext.android.inject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.log

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AddNoteViewModels by inject()
    private val handler = Handler(Looper.getMainLooper())
    private val timeFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    private val dataFormatter = SimpleDateFormat("dd MMMM", Locale.getDefault())
    private val convertor = DateConvertors()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
     setDateTime()
        observeViewModel()
        addYellowNote()
        addBlackNote()
        addBurgundy()
        setupTextVisibility()
        goBack()
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
                    Log.e("AddNoteFragment", "Ошибка: Заметка не добавлена", it.throwable)
                }

                AddNoteState.Loading -> {
                    Log.d("AddNoteFragment", "Загрузка...")
                }

                is AddNoteState.Success -> {
                    findNavController().navigateUp()
                    Log.d("AddNoteFragment", "Заметка успешно добавлена")
                }
            }
        }
    }

    private fun addYellowNote() = with(binding) {
        btnHome2.setOnClickListener {
            val title = someId.text.toString().trim()
            val note = some.text.toString().trim()
            if (title.isNotEmpty() && note.isNotEmpty()) {
                viewModel.precessIntent(
                    NoteIntent.AddNote(
                        Note(
                            title = title,
                            description = note,
                            date = dataFormatter.format(Date()),
                            time = timeFormatter.format(Date()),
                            color = "#EBE4C9"
                        )
                    )
                )
            } else {
                Log.d("AddNoteFragment", "Поля не должны быть пустыми")
            }
        }
    }

    private fun addBlackNote() = with(binding) {
        binding.btnBlack.setOnClickListener {
            val title = someId.text.toString().trim()
            val note = some.text.toString().trim()
            if (title.isNotEmpty() && note.isNotEmpty()) {
                viewModel.precessIntent(
                    NoteIntent.AddNote(
                        Note(
                            title = title,
                            description = note,
                            date = dataFormatter.format(Date()),
                            time = timeFormatter.format(Date()),
                            color = "#191818"
                        )
                    )
                )
            } else {
                Log.d("AddNoteFragment", "Поля не должны быть пустыми")
            }
        }
    }

    private fun addBurgundy() = with(binding) {
        binding.btnBurgundy.setOnClickListener {
            val title = someId.text.toString().trim()
            val note = some.text.toString().trim()
            if (title.isNotEmpty() && note.isNotEmpty()) {
                viewModel.precessIntent(
                    NoteIntent.AddNote(
                        Note(
                            title = title,
                            description = note,
                            date = dataFormatter.format(Date()),
                            time = timeFormatter.format(Date()),
                            color = "#571818"
                        )
                    )
                )
            }
        }
    }

    private fun setupTextVisibility() = with(binding) {
        val focusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                tvReady.visibility = View.VISIBLE
            }
        }
        someId.onFocusChangeListener = focusChangeListener
        some.onFocusChangeListener = focusChangeListener

        tvReady.setOnClickListener {
            tvReady.visibility = View.GONE
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