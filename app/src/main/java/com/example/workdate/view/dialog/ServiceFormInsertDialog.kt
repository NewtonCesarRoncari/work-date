package com.example.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.workdate.delegate.ServiceDialogListener

class ServiceFormInsertDialog(
    viewGroup: ViewGroup,
    context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = "New Service"

    fun initServiceFormDialog(listener: ServiceDialogListener) {
        super.inflateForm(listener)
    }
}
