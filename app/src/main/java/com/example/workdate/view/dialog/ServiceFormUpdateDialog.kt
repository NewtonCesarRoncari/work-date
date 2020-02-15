package com.example.workdate.view.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.workdate.model.Service
import kotlinx.android.synthetic.main.service_formulary.view.*

class ServiceFormUpdateDialog(
    viewGroup: ViewGroup,
    context: Context
) : ServiceFormDialog(context, viewGroup) {

    private val fieldID = viewCreated.formulary_service_id
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