package com.example.workdate.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workdate.R
import com.example.workdate.model.Client
import kotlinx.android.synthetic.main.client_formulary.view.*

class ClientFormDialog(
    private val viewGroup: ViewGroup,
    private val context: Context
) {

    private val viewCreated = initView()

    fun initClientFormDialog(listener: ClientDialogListener) {
        inflateForm(listener)
    }

    private fun inflateForm(listener: ClientDialogListener) {
        AlertDialog.Builder(context)
            .setTitle(R.string.title_form_new_client)
            .setView(viewCreated)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val clientName = viewCreated.client_formulary_name.text.toString().trim()
                val clientAddress = viewCreated.client_formulary_address.text.toString().trim()
                val clientPhone = viewCreated.client_formulary_phone.text.toString().trim()

                val client =
                    Client(name = clientName, address = clientAddress, phone = clientPhone)

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

interface ClientDialogListener {
    fun listener(client: Client)
}