package com.br.workdate.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import com.br.workdate.R
import com.br.workdate.databinding.ServiceFormularyBinding
import com.br.workdate.model.Service
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.service_formulary.view.*

abstract class ServiceFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup?
) {
    protected lateinit var dataBinding: ServiceFormularyBinding
    private val viewCreated = initView()
    protected val fieldDescription: TextInputEditText = viewCreated.service_form_description
    protected val fieldValue: TextInputEditText = viewCreated.service_form_value
    protected abstract val titleDialog: String

    protected fun inflateForm(dialogClickListener: (service: Service) -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleDialog)
            .setView(viewCreated)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val serviceData = dataBinding.service

                if (serviceData != null)
                    dialogClickListener(serviceData.returnService())
            }
            .setNegativeButton(R.string.negative_button_name, null)
            .setView(viewCreated)
            .show()
    }

    private fun initView(): View {
        val inflater = LayoutInflater.from(context)
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.service_formulary,
            viewGroup,
            false
        )
        return dataBinding.root
    }

    protected fun formatFieldForMoneyMask(text: CharSequence?) {
        if (!text.toString().matches("^\\$(\\d{1,3}(,\\d{3})*|(\\d+))(\\.\\d{2})?$".toRegex())) {
            val originalCursorPosition: Int = fieldValue.selectionStart
            var cursorOffset = 0
            val cursorAtEnd = originalCursorPosition == text.toString().length
            val userInput = "" + text.toString().replace("[^\\d]".toRegex(), "")
            val cashAmountBuilder = StringBuilder(userInput)
            while (cashAmountBuilder.length > 3 && cashAmountBuilder[0] == '0') {
                cashAmountBuilder.deleteCharAt(0)
                cursorOffset--
            }
            while (cashAmountBuilder.length < 3) {
                cashAmountBuilder.insert(0, '0')
                cursorOffset++
            }
            cashAmountBuilder.insert(cashAmountBuilder.length - 2, '.')
            cashAmountBuilder.insert(0, '$')
            fieldValue.setText(cashAmountBuilder.toString())
            fieldValue.setSelection(
                if (cursorAtEnd) fieldValue.text.toString()
                    .length else originalCursorPosition + cursorOffset
            )
        }
    }
}