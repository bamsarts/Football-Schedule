package me.bamsarts.footballschedule.utils

import android.annotation.SuppressLint
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.GONE
}

fun String.formatDate(fromDateFormat:String="dd/MM/yy", toDateFormat:String = "E, dd MMM yyyy") : String {
    val date = SimpleDateFormat(fromDateFormat).parse(this)
    val dateFormatter = SimpleDateFormat(toDateFormat)
    return dateFormatter.format(date)
}

fun String.parse(delimiter: String = ";", replacement: String = System.getProperty("line.separator") ) : String {
    return this.replace(delimiter, replacement)
}

@SuppressLint("SimpleDateFormat")
fun toSimpleString(date: Date?): String = with(date ?: Date()){
    SimpleDateFormat("EEE, dd MMM yyy").format(this)
}