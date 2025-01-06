package com.example.myapplication.components

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class TimeConverter {

    @SuppressLint("DefaultLocale", "SimpleDateFormat")
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        @SuppressLint("DefaultLocale")
        fun convertToHours(milliseconds: Long): String {
            val hours = TimeUnit.MILLISECONDS.toHours(milliseconds)
            val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60
            val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60

            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }

        fun formatDate(date: Date): String {
            return dateFormat.format(date)
        }
    }

}