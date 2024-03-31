package com.example.world_present_socialnetwork.utils

import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

object Common {
    const val baseURL = "http://192.168.0.106:3000"
    fun formatDateTime(dateTime: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val utcTimeZone = TimeZone.getTimeZone("UTC")
        val localTimeZone = TimeZone.getDefault()

        val date = inputFormat.parse(dateTime)
        outputFormat.timeZone = localTimeZone
        return outputFormat.format(date)
    }
}