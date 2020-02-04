package com.example.workdate.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workdate.R
import com.example.workdate.model.Service
import com.example.workdate.view.recyclerview.adapter.ServiceAdapter
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val services = listOf(
            Service(name = "A new job", description = "programming", value = BigDecimal(250.0)),
            Service(name = "A new job", description = "programming", value = BigDecimal(150.0)),
            Service(name = "A new job", description = "programming", value = BigDecimal(200.0)),
            Service(name = "A new job", description = "programming", value = BigDecimal(500.0))
        )

        new_service.setOnClickListener {
            val viewCreated = LayoutInflater.from(context).inflate(
                R.layout.service_formulary,
                getView() as ViewGroup,
                false
            )

            AlertDialog.Builder(context)
                .setTitle(R.string.title_form_new_service).setView(viewCreated).show()
        }
        initServiceAdapter(services)
    }

    private fun initServiceAdapter(services: List<Service>) {
        val adapter = context?.let { context -> ServiceAdapter(context, services) }
        service_list_rv.adapter = adapter
    }

}