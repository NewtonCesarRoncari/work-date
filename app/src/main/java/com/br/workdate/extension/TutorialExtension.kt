package com.br.workdate.extension

import android.graphics.Rect

fun findIconInWindow(width: Int, height: Int): Rect {
    val x = width * 2
    val horizontalDistance = x - (x * 0.0625)
    val verticalDistance = height * 0.15325

    return Rect(
        0,
        0,
        horizontalDistance.toInt(),
        verticalDistance.toInt()
    )
}