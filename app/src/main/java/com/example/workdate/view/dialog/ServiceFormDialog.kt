package com.example.workdate.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workdate.R
import com.example.workdate.model.Service
import kotlinx.android.synthetic.main.service_formulary.view.*
import java.math.BigDecimal

class ServiceFormDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCreated = initView()

    fun initServiceFormDialog(
        listener: ServiceDialogListener
    ) {
        inflateForm(listener)
    }

    private fun inflateForm(
        listener: ServiceDialogListener
    ) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_form_new_service)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val serviceDescription =
                    viewCreated.service_formulary_description.text.toString().trim()
                val serviceStringValue =
                    viewCreated.service_formulary_value.text.toString().trim()

                val serviceValue = tryParseBigDecimal(serviceStringValue)

                val service = Service(
                    name = serviceDescription,
                    description = "programming",
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

interface ServiceDialogListener {
    fun listener(service: Service)
}