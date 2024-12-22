package com.example.kotlin3_3.data.local.room.convertors

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class DateConvertors {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").apply {
        timeZone = TimeZone.getDefault()
    }

    private val dateOnlyFormat = SimpleDateFormat("dd MMMM").apply {
        timeZone = TimeZone.getDefault()
    }

    private val timeOnlyFormat = SimpleDateFormat("HH:mm").apply {
        timeZone = TimeZone.getDefault()
    }

    @TypeConverter
    fun fromDate(date: Date): String? {
        return dateFormat.format(date)
    }

    fun formatDateOnly(date: Date): String? {
        return dateOnlyFormat.format(date)
    }

    fun formatTimeOnly(date: Date): String? {
        return timeOnlyFormat.format(date)
    }

    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let { dateFormat.parse(it) }
    }
}
