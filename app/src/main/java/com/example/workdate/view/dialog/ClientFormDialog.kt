package com.example.workdate.view.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.workdate.R
import com.example.workdate.extension.returnUUID
import com.example.workdate.model.Client
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.client_formulary.view.*

abstract class ClientFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {
    private val viewCreated = initView()
    protected val fieldID: TextView = viewCreated.client_formulary_id
    protected val fieldName: TextInputEditText = viewCreated.client_formulary_name
    protected val fieldAddress: TextInputEditText = viewCreated.client_formulary_address
    protected val fieldPhone: TextInputEditText = viewCreated.client_formulary_phone
    protected abstract val titleDialog: String

    protected fun inflateForm(dialogClickListener: (client: Client) -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(titleDialog)
            .setView(viewCreated)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val clientName = fieldName.text.toString().trim()
                val clientAddress = fieldAddress.text.toString().trim()
                val clientPhone = fieldPhone.text.toString().trim()

                val client = Client(
                    id = fieldID.text.toString().returnUUID(),
                    name = clientName,
                    address = clientAddress,
                    phone = clientPhone
                )

                dialogClickListener(client)
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