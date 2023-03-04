package com.testapp.domain

import android.text.format.DateUtils
import com.testapp.R
import com.testapp.utils.ApplicationContextSingleton
import java.text.SimpleDateFormat
import java.util.*

fun String.getFormattedTime(formatInput: String, formatOutput: String): String {
    var result = "-"
    try {
        val date = SimpleDateFormat(formatInput, Locale.US).parse(this)
        if (date != null) {
            val sdf = SimpleDateFormat(formatOutput, Locale.getDefault())
            result = sdf.format(date)
        }
    } catch (e: Exception) {

    }
    return result
}

fun String.getPrettyFormattedTime(formatInput: String, formatOutput: String): String {
    var result = "-"
    try {
        val date = SimpleDateFormat(formatInput, Locale.US).parse(this)
        if (date != null) {
            val sdf = SimpleDateFormat(formatOutput, Locale.getDefault())
            result = sdf.format(date)
            if (isToday(date)) {
                result = ApplicationContextSingleton.getString(R.string.today)
            } else if (isTomorrow(date)) {
                result = ApplicationContextSingleton.getString(R.string.tomorrow)
            }
        }
    } catch (e: Exception) {

    }
    return result
}

fun isToday(d: Date): Boolean {
    return DateUtils.isToday(d.time)
}
fun isTomorrow(d: Date): Boolean {
    return DateUtils.isToday(d.time - DateUtils.DAY_IN_MILLIS)
}