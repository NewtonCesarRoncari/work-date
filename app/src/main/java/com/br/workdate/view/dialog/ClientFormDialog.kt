package com.br.workdate.view.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.br.workdate.R
import com.br.workdate.databinding.ClientFormularyBinding
import com.br.workdate.model.Client
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.client_formulary.view.*

abstract class ClientFormDialog(
    private val context: Context,
    private val viewGroup: ViewGroup
) {
    protected lateinit var dataBinding: ClientFormularyBinding
    private val viewCreated = initView()
    protected val fieldName: TextInputEditText = viewCreated.client_form_name
    protected abstract val titleDialog: String

    protected fun inflateForm(dialogClickListener: (client: Client) -> Unit) {
        MaterialAlertDialogBuilder(context)
            .setTitle(titleDialog)
            .setView(viewCreated)
            .setPositiveButton(R.string.positive_button_name) { _, _ ->
                val clientData = dataBinding.client

                if (clientData != null)
                    dialogClickListener(clientData.returnClient())
            }
            .setNegativeButton(R.string.negative_button_name, null)
            .show()
    }

    private fun initView(): View {
        val inflater = LayoutInflater.from(context)
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.client_formulary,
            viewGroup,
            false
        )
        return dataBinding.root
    }
}