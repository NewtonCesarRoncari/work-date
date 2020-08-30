package com.br.workdate.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.br.workdate.R
import com.br.workdate.extension.returnUUID
import com.br.workdate.model.Service
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.service_formulary.view.*
import java.math.BigDecimal

abstract class ServiceFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup?
) {
    private val viewCreated = initView()
    protected val fieldID: TextView = viewCreated.service_formulary_id
    protected val fieldDescription: TextInputEditText = viewCreated.service_formulary_description
    protected val fieldValue: TextInputEditText = viewCreated.service_formulary_value
    protected abstract val titleDialog: String

    protected fun inflateForm(
        dialogClickListener: (service: Service) -> Unit
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleDialog)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val serviceDescription = fieldDescription.text.toString().trim()
                val serviceStringValue = fieldValue.text.toString().trim()
                val serviceValue = tryParseBigDecimal(serviceStringValue)

                val service = Service(
                    id = fieldID.text.toString().returnUUID(),
                    description = serviceDescription,
                    value = serviceValue
                )

                dialogClickListener(service)
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