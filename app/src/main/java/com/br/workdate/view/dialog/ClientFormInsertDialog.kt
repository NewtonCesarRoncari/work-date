package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Client
import com.br.workdate.view.databinding.ClientData

class ClientFormInsertDialog(
    viewGroup: ViewGroup,
    private val context: Context
) : ClientFormDialog(context, viewGroup) {

    private val emptyString = ""
    override val titleDialog: String
        get() = context.getString(R.string.title_form_new_client)

    fun initClientFormDialog(dialogClickListener: (client: Client) -> Unit) {
        dataBinding.client = ClientData(returnEmptyClient())
        super.inflateForm(dialogClickListener)
    }

    private fun returnEmptyClient() = Client(
        emptyString,
        emptyString,
        emptyString,
        emptyString
    )
}