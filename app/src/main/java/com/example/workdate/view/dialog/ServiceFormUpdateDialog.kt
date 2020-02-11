package com.example.workdate.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.ViewGroup
import com.example.workdate.delegate.ServiceDialogListener
import com.example.workdate.model.Service
import kotlinx.android.synthetic.main.service_formulary.view.*

class ServiceFormUpdateDialog(
    viewGroup: ViewGroup,
    context: Context
) : ServiceFormDialog(context, viewGroup) {

    private val fieldID = viewCreated.formulary_service_id
    override val titleDialog = "Update Service"

    fun initServiceFormDialog(service: Service, listener: ServiceDialogListener) {
        loadFieldDataDialog(service)
        super.inflateForm(listener)
    }

    @SuppressLint("SetTextI18n")
    private fun loadFieldDataDialog(service: Service) {
        fieldID.text = service.id
        fieldDescription.setText(service.description)
        fieldValue.setText(service.value.toString())
    }

}