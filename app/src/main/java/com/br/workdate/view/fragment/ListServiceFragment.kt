package com.br.workdate.view.fragment

import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.extension.showDialogMessage
import com.br.workdate.model.Service
import com.br.workdate.view.databinding.ServiceData
import com.br.workdate.view.dialog.ServiceFormUpdateDialog

class ListServiceFragment : BaseListServiceFragment() {

    override fun doInItemClickListener(service: Service) {
        val serviceData = ServiceData(service)
        callUpdateDialog(serviceData)
    }

    private fun callUpdateDialog(service: ServiceData) {
        context?.let { context ->
            ServiceFormUpdateDialog(view as ViewGroup, context)
                .initServiceFormDialog(service) { serviceReturned ->
                    viewModel.update(serviceReturned,
                        inFailureCase = {
                            activity?.runOnUiThread {
                                showDialogMessage(
                                    getString(R.string.error),
                                    getString(R.string.message_service_description_already_exists),
                                    requireContext()
                                )
                            }
                        }, inSuccessCase = {
                            activity?.runOnUiThread {
                                showSnackBar(serviceReturned, getString(R.string.updated))
                            }
                        })
                }
        }
    }

}
