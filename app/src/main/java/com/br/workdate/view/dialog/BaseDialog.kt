package com.br.workdate.view.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BaseDialog(private val context: Context) {

    fun showErrorRemoveDialog(message: String) {
        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            setTitle("Error")
            setMessage(message)
            setPositiveButton("Ok", null)
            show()
        }
    }

}
