package com.example.kotlin3_3.ui.adapters

import android.content.res.ColorStateList
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlin3_3.R
import com.example.kotlin3_3.data.local.room.entities.Note
import com.example.kotlin3_3.databinding.NoteItemBinding

class NoteAdapter(
    private val onEditClick: (Note) -> Unit,
    private val onDeleteClick: (Note) -> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    private var searchQuery: String = ""
    private var titleSizeSp: Float = 16f
    private var textSizeSp: Float = 14f

    fun updateTextSize(sizeIndex: Int) {
        titleSizeSp = when (sizeIndex) {
            0 -> 14f
            1 -> 16f
            2 -> 20f
            else -> 16f
        }
        textSizeSp = when (sizeIndex) {
            0 -> 12f
            1 -> 14f
            2 -> 18f
            else -> 14f
        }
        notifyItemRangeChanged(0, currentList.size)
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(note: Note) {
            binding.tvTitleNote.text = if (searchQuery.isEmpty()) {
                note.title
            } else {
                highlightText(note.title, searchQuery)
            }
            binding.tvNote.text = note.description
            binding.tvDateTime.text = note.date
            binding.tvTime.text = note.time

            binding.tvTitleNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, titleSizeSp)
            binding.tvNote.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSizeSp)

            try {
                val color = Color.parseColor(note.color)
                binding.colorNote.setCardBackgroundColor(ColorStateList.valueOf(color))
            } catch (e: IllegalArgumentException) {
                Log.e("NoteAdapter", "Ошибка парсинга цвета: ${note.color}")
            }

            itemView.setOnClickListener { onEditClick(note) }
            itemView.setOnLongClickListener {
                onDeleteClick(note)
                true
            }
        }

        private fun highlightText(text: String, query: String): SpannableString {
            val spannableString = SpannableString(text)
            if (query.isEmpty()) return spannableString

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
        notifyItemRangeChanged(0, currentList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem == newItem
        }
    }
}
