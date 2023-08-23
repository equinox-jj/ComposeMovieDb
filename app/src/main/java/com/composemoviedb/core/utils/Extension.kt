package com.composemoviedb.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun String.dateFormatter(): String {
    val format = "dd MMM yyyy HH:mm:ss" // you can add the format you need
    val sdf = SimpleDateFormat(format, Locale.getDefault()) // default local
    sdf.timeZone = TimeZone.getDefault() // set anytime zone you need
    return sdf.format(Date(this.toLong() * 1000))
}