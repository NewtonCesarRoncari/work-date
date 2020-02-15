package com.example.workdate.view.dialog

import android.content.Context
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.example.workdate.model.Client

class ClientFormUpdateDialog(
    viewGroup: ViewGroup,
    context: Context
) : ClientFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = "Update Client"

    fun initClientFormDialog(client: Client, dialogClickListener: (client: Client) -> Unit) {
        loadFieldDataDialog(client)
        super.inflateForm(dialogClickListener)
    }

    private fun loadFieldDataDialog(client: Client) {
        fieldID.text = client.id
        fieldID.visibility = VISIBLE
        fieldName.setText(client.name)
        fieldAddress.setText(client.address)
        fieldPhone.setText(client.phone)
    }

}