package com.br.workdate.view.dialog

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BaseDialog(private val context: Context) {

    fun showErrorRemoveDialog() {
        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            setTitle("Error")
            setMessage("this record is linked to a schedule")
            setPositiveButton("Ok", null)
            show()
        }
    }

}
