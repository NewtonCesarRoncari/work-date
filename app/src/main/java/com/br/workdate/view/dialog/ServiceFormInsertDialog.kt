package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.br.workdate.R

class ServiceFormInsertDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = context.getString(R.string.title_form_new_service)

    fun initServiceFormDialog(dialogClickListener: (service: com.br.workdate.model.Service) -> Unit) {
        super.inflateForm(dialogClickListener)
    }
}
