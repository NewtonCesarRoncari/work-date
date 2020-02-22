package com.br.workdate.extension

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Date.parseFormatHour(): String {
    val formatHour = SimpleDateFormat("HH:mm")
    return formatHour.format(this)
}