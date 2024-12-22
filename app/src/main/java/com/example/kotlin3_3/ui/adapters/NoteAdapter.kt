package com.example.kotlin3_3.ui.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.NoteItemBinding

class NoteAdapter(private val onDeleteClick: (Note) -> Unit) :
    RecyclerView.Adapter<NoteAdapter.NoteViwHolder>() {

    private var noteList = listOf<Note>()
    private var searchQuery: String = ""

    fun setNoteList(noteList: List<Note>) {
        this.noteList = noteList
        notifyDataSetChanged()
    }

    inner class NoteViwHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(notesModel: Note) {
            if (searchQuery.isEmpty()) {
                binding.tvTitleNote.text = notesModel.title
            } else {
                binding.tvTitleNote.text = highlightText(notesModel.title, searchQuery)
            }
            binding.tvNote.text = notesModel.description
            binding.tvDateTime.text = notesModel.date
            binding.tvTime.text = notesModel.time
            try {
                val color = Color.parseColor(notesModel.color)
                binding.colorNote.setCardBackgroundColor(ColorStateList.valueOf(color))
            } catch (e: IllegalArgumentException) {
                Log.e("tag", "ошибка в адаптере в самом цвете")
            }
        }

        private fun highlightText(text: String, query: String): SpannableString {
            val spannableString = SpannableString(text)
            if (query.isEmpty()) {
                return spannableString
            }
            val color = ContextCompat.getColor(binding.root.context, R.color.blackGray)
            var startIndex = text.lowercase().indexOf(query.lowercase())
            while (startIndex >= 0) {
                val endIndex = startIndex + query.length
                spannableString.setSpan(
                    ForegroundColorSpan(color),
                    startIndex,
                    endIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                startIndex = text.lowercase().indexOf(query.lowercase(), endIndex)
            }

            return spannableString
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        Log.d("NotesAdapter", "Search query updated: $query")
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViwHolder {
        return NoteViwHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: NoteViwHolder, position: Int) {
        val note = noteList[position]
        holder.onBind(note)
        holder.itemView.setOnLongClickListener {
            onDeleteClick(note)
            true
        }
    }
}