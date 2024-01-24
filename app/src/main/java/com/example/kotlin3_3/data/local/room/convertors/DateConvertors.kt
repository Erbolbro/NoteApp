package com.example.kotlin3_3.data.local.room.convertors

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class DateConvertors {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    @TypeConverter
    fun fromDate(date: java.sql.Date?): String? {
        return date?.let { dateFormat.format(it) }
    }

    @TypeConverter
    fun toDate(dateString: String?): Date? {
        return dateString?.let { dateFormat.parse(it) }
    }
}