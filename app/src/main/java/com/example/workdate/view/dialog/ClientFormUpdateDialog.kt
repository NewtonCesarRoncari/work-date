package com.example.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.example.workdate.delegate.ClientDialogListener
import com.example.workdate.model.Client
import kotlinx.android.synthetic.main.client_formulary.view.*

class ClientFormUpdateDialog(
    viewGroup: ViewGroup,
    context: Context
) : ClientFormDialog(context, viewGroup) {

    private val fieldID = super.viewCreated.formulary_client_id
    override val titleDialog: String
        get() = "Update Client"

    fun initClientFormDialog(client: Client, listener: ClientDialogListener) {
        loadFieldDataDialog(client)
        super.inflateForm(listener)
    }

    private fun loadFieldDataDialog(client: Client) {
        fieldID.text = client.id
        fieldName.setText(client.name)
        fieldAddress.setText(client.address)
        fieldPhone.setText(client.phone)
    }

}