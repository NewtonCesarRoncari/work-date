package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.model.Service
import com.br.workdate.view.dialog.ServiceFormUpdateDialog

class ListServiceFragment : BaseListServiceFragment() {

    override fun doInItemClickListener(service: Service) = callUpdateDialog(service)

    private fun callUpdateDialog(service: Service) {
        context?.let { context ->
            ServiceFormUpdateDialog(view as ViewGroup, context)
                .initServiceFormDialog(service) { serviceReturned ->
                    viewModel.update(serviceReturned)
                    super.showSnackBar(serviceReturned, "updated")
                }
        }
    }

}
