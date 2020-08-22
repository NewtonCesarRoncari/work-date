package com.br.workdate.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Service

class ServiceFormUpdateDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = context.getString(R.string.title_form_update_service)

    fun initServiceFormDialog(service: Service, dialogClickListener: (service: Service) -> Unit) {
        loadFieldDataDialog(service)
        super.inflateForm(dialogClickListener)
    }

    @SuppressLint("SetTextI18n")
    private fun loadFieldDataDialog(service: Service) {
        fieldID.visibility = VISIBLE
        fieldDescription.isFocusable = false
        fieldID.text = service.id
        fieldDescription.setText(service.description)
        fieldValue.setText(service.value.toString())
    }

}