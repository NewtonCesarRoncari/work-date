package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.extension.showDialogMessage
import com.br.workdate.model.Client
import com.br.workdate.view.databinding.ClientData
import com.br.workdate.view.dialog.ClientFormUpdateDialog

class ListClientFragment : BaseListClientFragment() {

    override fun doInItemClickListener(client: Client) {
        val clientData by lazy { ClientData(client) }
        callUpdateDialog(clientData)
    }

    override fun doInOnCreateView() {} //method implemented in delegate

    private fun callUpdateDialog(client: ClientData) {
        context?.let { context ->
            ClientFormUpdateDialog(view as ViewGroup, context)
                .initClientFormDialog(client) { clientReturned ->
                    viewModel.update(clientReturned,
                        inFailureCase = {
                            activity?.runOnUiThread {
                                showDialogMessage(
                                    getString(R.string.error),
                                    getString(R.string.message_client_name_already_exists),
                                    requireContext()
                                )
                            }
                        }, inSuccessCase = {
                            activity?.runOnUiThread {
                                showSnackBar(clientReturned, getString(R.string.updated))
                            }
                        })
                }
        }
    }
}