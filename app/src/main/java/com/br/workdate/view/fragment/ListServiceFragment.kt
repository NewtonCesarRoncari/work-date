package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.model.Service
import com.br.workdate.view.dialog.ServiceFormInsertDialog
import com.br.workdate.view.dialog.ServiceFormUpdateDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class ListServiceFragment : BaseListServiceFragment() {

    override fun implementIfWannaHideFab(fab: FloatingActionButton) {}

    override fun doInFabClickListener() = callInsertDialog()

    override fun doInItemClickListener(service: Service) = callUpdateDialog(service)

    override fun doInItemLongClickListener(service: Service) {
        super.viewModel.remove(service)
        showSnackBar(service, "removed")
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ServiceFormInsertDialog(view as ViewGroup, context)
                .initServiceFormDialog { serviceReturned ->
                    viewModel.insert(serviceReturned)
                    showSnackBar(serviceReturned, "saved")
                }
        }
    }

    private fun callUpdateDialog(service: Service) {
        context?.let { context ->
            ServiceFormUpdateDialog(view as ViewGroup, context)
                .initServiceFormDialog(service) { serviceReturned ->
                    viewModel.update(serviceReturned)
                    showSnackBar(serviceReturned, "updated")
                }
        }
    }

    private fun showSnackBar(service: Service, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${service.description} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }

}
