package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup

class ServiceFormInsertDialog(
    viewGroup: ViewGroup,
    context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = "New Service"

    fun initServiceFormDialog(dialogClickListener: (service: com.br.workdate.model.Service) -> Unit) {
        super.inflateForm(dialogClickListener)
    }
}
