package com.br.workdate.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.br.workdate.model.Service

class ServiceFormUpdateDialog(
    viewGroup: ViewGroup,
    context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog = "Update Service"

    fun initServiceFormDialog(service: Service, dialogClickListener: (service: Service) -> Unit) {
        loadFieldDataDialog(service)
        super.inflateForm(dialogClickListener)
    }

    @SuppressLint("SetTextI18n")
    private fun loadFieldDataDialog(service: Service) {
        fieldID.text = service.id
        fieldID.visibility = VISIBLE
        fieldDescription.setText(service.description)
        fieldValue.setText(service.value.toString())
    }

}