package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.br.workdate.R
import com.br.workdate.model.Service
import com.br.workdate.view.dialog.ServiceFormInsertDialog
import com.br.workdate.view.recyclerview.adapter.ServiceAdapter
import com.br.workdate.view.viewmodel.ServiceViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_service.*
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseListServiceFragment : Fragment() {

    protected val viewModel: ServiceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        service_list_animation.setAnimation("anim/client_empty.json")

        new_service.setOnClickListener {
            callInsertDialog()
        }

        viewModel.listAll().observe(viewLifecycleOwner, androidx.lifecycle.Observer { serviceList ->
            ifEmptyPlayAnimation(serviceList)
            initServiceAdapter(serviceList)
        })
    }

    private fun initServiceAdapter(services: MutableList<Service>) {
        val adapter = context?.let { context -> ServiceAdapter(context, services) }
        service_list_rv.adapter = adapter
        adapter!!.onItemClickListener = { service ->
            doInItemClickListener(service)
        }
        adapter.onItemLongClickListener = { service ->
            viewModel.remove(service)
            showSnackBar(service, "removed")
        }
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Service>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            service_list_animation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(service_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
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

    protected fun showSnackBar(service: Service, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${service.description} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }
    abstract fun doInItemClickListener(service: Service)

}