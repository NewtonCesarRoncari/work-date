package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.br.workdate.R
import com.br.workdate.model.Service
import com.br.workdate.view.databinding.ServiceData

class ServiceFormUpdateDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ServiceFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = context.getString(R.string.title_form_update_service)

    fun initServiceFormDialog(
        service: ServiceData,
        dialogClickListener: (service: Service) -> Unit
    ) {
        dataBinding.service = service
        fieldDescription.isFocusable = false
        fieldValue.doOnTextChanged { text, _, _, _ ->
            formatFieldForMoneyMask(text)
        }
        super.inflateForm(dialogClickListener)
    }
}