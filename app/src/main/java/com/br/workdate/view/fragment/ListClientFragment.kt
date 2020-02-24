package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.model.Client
import com.br.workdate.view.dialog.ClientFormInsertDialog
import com.br.workdate.view.dialog.ClientFormUpdateDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListClientFragment : BaseListClientFragment() {

    override fun implementIfWannaHideFab(fab: FloatingActionButton) {}

    override fun doInFabClickListener() = callInsertDialog()

    override fun doInItemClickListener(client: Client) = callUpdateDialog(client)

    override fun doInItemLongClickListener(client: Client) {
        super.viewModel.remove(client)
        showSnackBar(client, "removed")
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ClientFormInsertDialog(view as ViewGroup, context)
                .initClientFormDialog { clientReturned ->
                    viewModel.insert(clientReturned)
                    showSnackBar(clientReturned, "saved")
                }
        }
    }

    private fun callUpdateDialog(client: Client) {
        context?.let { context ->
            ClientFormUpdateDialog(view as ViewGroup, context)
                .initClientFormDialog(client) { clientReturned ->
                    viewModel.update(clientReturned)
                    showSnackBar(clientReturned, "updated")
                }
        }
    }

    private fun showSnackBar(client: Client, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${client.name} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }
}