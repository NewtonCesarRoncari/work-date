package com.br.workdate.extension

import android.content.Context
import android.graphics.Point
import android.view.Display
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showDialogMessage(title: String, message: String, context: Context) {
    val builder = MaterialAlertDialogBuilder(context)
    with(builder) {
        setTitle(title)
        setMessage(message)
        setPositiveButton(context.getString(R.string.ok), null)
        show()
    }

}

fun getWindow(activity: FragmentActivity?): Pair<Int, Int> {
    val display: Display = activity?.windowManager?.defaultDisplay!!
    val size = Point()
    display.getSize(size)
    val width: Int = size.x
    val height: Int = size.y
    return Pair(width, height)
}