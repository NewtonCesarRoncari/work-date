package com.br.workdate.extension

import android.content.Context
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