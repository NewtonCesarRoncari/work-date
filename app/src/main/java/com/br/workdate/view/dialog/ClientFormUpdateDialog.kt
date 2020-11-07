package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Client
import com.br.workdate.view.databinding.ClientData

class ClientFormUpdateDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ClientFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = context.getString(R.string.title_form_update_client)

    fun initClientFormDialog(client: ClientData, dialogClickListener: (client: Client) -> Unit) {
        dataBinding.client = client
        fieldName.isFocusable = false
        super.inflateForm(dialogClickListener)
    }
}