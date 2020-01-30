package com.example.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workdate.R
import com.example.workdate.model.Service
import com.example.workdate.view.recyclerview.adapter.ServiceAdapter
import kotlinx.android.synthetic.main.fragment_list_service.*

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
            Service("", "A new job", "programming"),
            Service("", "A new job", "programming"),
            Service("", "A new job", "programming"),
            Service("", "A new job", "programming")
        )

        initServiceAdapter(services)
    }

    private fun initServiceAdapter(services: List<Service>) {
        val adapter = context?.let { context -> ServiceAdapter(context, services) }
        service_list_rv.adapter = adapter
    }

}