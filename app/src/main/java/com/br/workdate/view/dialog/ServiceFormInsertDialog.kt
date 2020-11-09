package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Service
import com.br.workdate.view.databinding.ServiceData
import java.math.BigDecimal

class ServiceFormInsertDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ServiceFormDialog(context, viewGroup) {

    private val emptyString = ""
    override val titleDialog: String
        get() = context.getString(R.string.title_form_new_service)

    fun initServiceFormDialog(dialogClickListener: (service: Service) -> Unit) {
        dataBinding.service = ServiceData(returnEmptyService())
        super.inflateForm(dialogClickListener)
    }

    private fun returnEmptyService() = Service(emptyString, emptyString, BigDecimal.ZERO)
}
