package com.example.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.workdate.R
import com.example.workdate.model.Client
import com.example.workdate.view.dialog.ClientFormInsertDialog
import com.example.workdate.view.dialog.ClientFormUpdateDialog
import com.example.workdate.view.recyclerview.adapter.ClientAdapter
import com.example.workdate.view.viewmodel.ClientViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_client.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListClientFragment : Fragment() {

    private val viewModel: ClientViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        new_client.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, Observer { clientList ->
            initClientAdapter(clientList)
        })
    }

    private fun initClientAdapter(clients: MutableList<Client>) {
        val adapter = context?.let { context -> ClientAdapter(context, clients) }
        client_list_rv.adapter = adapter
        adapter!!.onItemClickListener = { client ->
            callUpdateDialog(client)
        }
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ClientFormInsertDialog(view as ViewGroup, context)
                .initClientFormDialog { clientReturned ->
                    viewModel.insert(clientReturned)
                    showSnackBar(clientReturned)
                }
        }
    }

    private fun callUpdateDialog(client: Client) {
        context?.let { context ->
            ClientFormUpdateDialog(view as ViewGroup, context)
                .initClientFormDialog(client) { clientReturned ->
                    viewModel.update(clientReturned)
                    showSnackBar(clientReturned)
                }
        }
    }

    private fun showSnackBar(client: Client) {
        view?.let { view ->
            Snackbar.make(view, "${client.name} saved", Snackbar.LENGTH_SHORT).show()
        }
    }
}