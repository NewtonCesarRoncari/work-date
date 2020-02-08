package com.example.workdate.view.dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workdate.R
import com.example.workdate.delegate.ServiceDialogListener
import com.example.workdate.model.Service
import kotlinx.android.synthetic.main.service_formulary.view.*
import java.math.BigDecimal

class ServiceFormUpdateDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCreated = initView()
    private val fieldID = viewCreated.formulary_service_id
    private val fieldDescription = viewCreated.service_formulary_description
    private val fieldValue = viewCreated.service_formulary_value

    fun initServiceFormDialog(service: Service, listener: ServiceDialogListener) {
        loadDataDialog(service)
        inflateForm(listener)
    }

    @SuppressLint("SetTextI18n")
    private fun loadDataDialog(service: Service) {
        fieldID.text = service.id
        fieldDescription.setText(service.description)
        fieldValue.setText(service.value.toString())
    }

    private fun inflateForm(
        listener: ServiceDialogListener
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_form_update_service)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val serviceDescription = fieldDescription.text.toString().trim()
                val serviceStringValue = fieldValue.text.toString().trim()
                val serviceValue = tryParseBigDecimal(serviceStringValue)

                val service = Service(
                    description = serviceDescription,
                    value = serviceValue
                )

                listener.listener(service)
            }
            .setNegativeButton(R.string.negative_button_name, null)
            .setView(viewCreated)
            .show()
    }

    private fun tryParseBigDecimal(serviceStringValue: String): BigDecimal {
        return try {
            BigDecimal(serviceStringValue)
        } catch (e: NumberFormatException) {
            BigDecimal.ZERO
        }
    }

    private fun initView(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.service_formulary,
            viewGroup,
            false
        )
    }
}

interface ServiceDialogUpdateListener {
    fun listener(service: Service)
}