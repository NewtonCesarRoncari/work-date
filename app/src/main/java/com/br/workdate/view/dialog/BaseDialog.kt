package com.br.workdate.view.dialog

import android.content.Context
import com.br.workdate.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class BaseDialog(private val context: Context) {

    fun showErrorRemoveDialog(message: String) {
        val builder = MaterialAlertDialogBuilder(context)
        with(builder) {
            setTitle(context.getString(R.string.error))
            setMessage(message)
            setPositiveButton(context.getString(R.string.ok), null)
            show()
        }
    }

}
