package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.br.workdate.R
import com.br.workdate.model.Client
import com.br.workdate.view.dialog.ClientFormInsertDialog
import com.br.workdate.view.dialog.ClientFormUpdateDialog
import com.br.workdate.view.recyclerview.adapter.ClientAdapter
import com.br.workdate.view.viewmodel.ClientViewModel
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
        client_list_animation.setAnimation("anim/client_empty.json")

        new_client.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, Observer { clientList ->
            ifEmptyPlayAnimation(clientList)
            initClientAdapter(clientList)
        })
    }

    private fun initClientAdapter(clients: MutableList<Client>) {
        val adapter = context?.let { context -> ClientAdapter(context, clients) }
        client_list_rv.adapter = adapter
        adapter!!.onItemClickListener = { client ->
            callUpdateDialog(client)
        }
        adapter.onItemLongClickListener = { client ->
            viewModel.remove(client)
            showSnackBar(client, "removed")
        }
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

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Client>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            client_list_animation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(client_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }
}