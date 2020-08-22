package com.br.workdate.view.dialog

import android.content.Context
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Client

class ClientFormUpdateDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ClientFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = context.getString(R.string.title_form_update_client)

    fun initClientFormDialog(client: Client, dialogClickListener: (client: Client) -> Unit) {
        loadFieldDataDialog(client)
        super.inflateForm(dialogClickListener)
    }

    private fun loadFieldDataDialog(client: Client) {
        fieldID.visibility = VISIBLE
        fieldName.isFocusable = false
        fieldID.text = client.id
        fieldName.setText(client.name)
        fieldAddress.setText(client.address)
        fieldPhone.setText(client.phone)
    }

}