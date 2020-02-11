package com.example.workdate.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workdate.R
import com.example.workdate.delegate.ServiceDialogListener
import com.example.workdate.model.Service
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.service_formulary.view.*
import java.math.BigDecimal

abstract class ServiceFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup?
) {
    protected val viewCreated = initView()
    protected val fieldDescription: TextInputEditText = viewCreated.service_formulary_description
    protected val fieldValue: TextInputEditText = viewCreated.service_formulary_value
    protected abstract val titleDialog: String

    protected fun inflateForm(
        listener: ServiceDialogListener
    ) {
        AlertDialog.Builder(context)
            .setTitle(titleDialog)
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