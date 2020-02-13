package com.example.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workdate.R
import com.example.workdate.model.Service
import com.example.workdate.view.dialog.ServiceFormInsertDialog
import com.example.workdate.view.dialog.ServiceFormUpdateDialog
import com.example.workdate.view.recyclerview.adapter.ServiceAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_service.*
import java.math.BigDecimal

class ListServiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_service, container, false)
    }

    private fun initServiceAdapter(services: List<Service>) {
        val adapter = context?.let { context -> ServiceAdapter(context, services) }
        service_list_rv.adapter = adapter
        adapter!!.onItemClickListener = { service ->
            callUpdateDialog(service)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val services = listOf(
            Service(description = "programming", value = BigDecimal(250.0)),
            Service(description = "programming", value = BigDecimal(150.0)),
            Service(description = "programming", value = BigDecimal(200.0)),
            Service(description = "programming", value = BigDecimal(500.0))
        )

        new_service.setOnClickListener {
            callInsertDialog()
        }
        initServiceAdapter(services)
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ServiceFormInsertDialog(view as ViewGroup, context)
                .initServiceFormDialog { serviceReturned ->
                    showSnackBar(serviceReturned)
                }
        }
    }

    private fun callUpdateDialog(service: Service) {
        context?.let { context ->
            ServiceFormUpdateDialog(view as ViewGroup, context)
                .initServiceFormDialog(service) { serviceReturned ->
                    showSnackBar(serviceReturned)
                }
        }
    }

    private fun showSnackBar(service: Service) {
        view?.let { view ->
            Snackbar.make(view, "${service.description} saved", Snackbar.LENGTH_SHORT).show()
        }
    }

}
