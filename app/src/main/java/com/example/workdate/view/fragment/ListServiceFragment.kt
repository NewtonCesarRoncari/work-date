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
import com.example.workdate.view.viewmodel.ServiceViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_service.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListServiceFragment : Fragment() {

    private val viewModel: ServiceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_service, container, false)
    }

    private fun initServiceAdapter(services: MutableList<Service>) {
        val adapter = context?.let { context -> ServiceAdapter(context, services) }
        service_list_rv.adapter = adapter
        adapter!!.onItemClickListener = { service ->
            callUpdateDialog(service)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        new_service.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, androidx.lifecycle.Observer { listService ->
            initServiceAdapter(listService)
        })
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ServiceFormInsertDialog(view as ViewGroup, context)
                .initServiceFormDialog { serviceReturned ->
                    viewModel.insert(serviceReturned)
                    showSnackBar(serviceReturned)
                }
        }
    }

    private fun callUpdateDialog(service: Service) {
        context?.let { context ->
            ServiceFormUpdateDialog(view as ViewGroup, context)
                .initServiceFormDialog(service) { serviceReturned ->
                    viewModel.update(serviceReturned)
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
