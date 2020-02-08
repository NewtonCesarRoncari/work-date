package com.example.workdate.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workdate.R
import com.example.workdate.delegate.ClientDialogListener
import com.example.workdate.model.Client
import kotlinx.android.synthetic.main.client_formulary.view.*

class ClientFormUpdateDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCreated = initView()
    private val fieldID = viewCreated.formulary_client_id
    private val fieldName = viewCreated.client_formulary_name
    private val fieldAddress = viewCreated.client_formulary_address
    private val fieldPhone = viewCreated.client_formulary_phone

    fun initClientFormDialog(client: Client, listener: ClientDialogListener) {
        loadDataDialog(client)
        inflateForm(listener)
    }

    private fun loadDataDialog(client: Client) {
        fieldID.text = client.id
        fieldName.setText(client.name)
        fieldAddress.setText(client.address)
        fieldPhone.setText(client.phone)
    }

    private fun inflateForm(listener: ClientDialogListener) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_form_update_client)
            .setView(viewCreated)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val clientName = fieldName.text.toString().trim()
                val clientAddress = fieldAddress.text.toString().trim()
                val clientPhone = fieldPhone.text.toString().trim()

                val client = Client(
                    name = clientName,
                    address = clientAddress,
                    phone = clientPhone
                )

                listener.listener(client)
            }
            .setNegativeButton(R.string.negative_button_name, null)
            .show()
    }

    private fun initView(): View {
        return LayoutInflater.from(context).inflate(
            R.layout.client_formulary,
            viewGroup,
            false
        )
    }
}