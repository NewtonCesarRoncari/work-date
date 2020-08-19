package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.model.Client
import com.br.workdate.view.dialog.BaseDialog
import com.br.workdate.view.dialog.ClientFormUpdateDialog

class ListClientFragment : BaseListClientFragment() {

    override fun doInItemClickListener(client: Client) = callUpdateDialog(client)
    override fun doInOnCreateView() {}

    private fun callUpdateDialog(client: Client) {
        context?.let { context ->
            ClientFormUpdateDialog(view as ViewGroup, context)
                .initClientFormDialog(client) { clientReturned ->
                    viewModel.update(clientReturned,
                        inFailureCase = {
                            activity?.runOnUiThread {
                                val baseDialog = BaseDialog(requireContext())
                                baseDialog.showErrorRemoveDialog("this client name already exists")
                            }
                        }, inSuccessCase = {
                            activity?.runOnUiThread {
                                showSnackBar(clientReturned, "updated")
                            }
                        })
                }
        }
    }
}