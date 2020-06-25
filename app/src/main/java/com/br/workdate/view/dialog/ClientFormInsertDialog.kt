package com.br.workdate.view.dialog

import android.content.Context
import android.view.ViewGroup
import com.br.workdate.model.Client

class ClientFormInsertDialog(
    viewGroup: ViewGroup,
    context: Context
) : ClientFormDialog(context, viewGroup) {

    override val titleDialog: String
        get() = "New Client"

    fun initClientFormDialog(dialogClickListener: (client: Client) -> Unit) {
        super.inflateForm(dialogClickListener)
    }
}