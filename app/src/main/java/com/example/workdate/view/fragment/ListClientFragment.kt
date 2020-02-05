package com.example.workdate.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.workdate.R
import com.example.workdate.model.Client
import com.example.workdate.view.recyclerview.adapter.ClientAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.client_formulary.view.*
import kotlinx.android.synthetic.main.fragment_list_client.*

class ListClientFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val clients = listOf(
            Client(
                name = "Name Example",
                phone = "(37) 9 9123-5555",
                address = "Rua Sebastião Alves de Oliveira, 293"
            ), Client(
                name = "Newton Cesar ",
                phone = "(37) 9 9123-5555",
                address = "Rua Sebastião Alves de Oliveira, 293"
            ), Client(
                name = "Newton Cesar",
                phone = "(37) 9 9123-5555",
                address = "Rua Sebastião Alves de Oliveira, 293"
            )
        )
        new_client.setOnClickListener {
            val viewCreated = LayoutInflater.from(context)
                .inflate(
                    R.layout.client_formulary,
                    getView() as ViewGroup,
                    false
                )

            AlertDialog.Builder(context)
                .setTitle(R.string.title_form_new_client)
                .setView(viewCreated)
                .setPositiveButton(R.string.positive_button_name) { dialog, which ->
                    val clientName = viewCreated.client_formulary_name.text.toString().trim()
                    val clientAddress = viewCreated.client_formulary_address.text.toString().trim()
                    val clientPhone = viewCreated.client_formulary_phone.text.toString().trim()

                    val client =
                        Client(name = clientName, address = clientAddress, phone = clientPhone)
                    Snackbar.make(view, "${client.name} Salvo com sucesso", Snackbar.LENGTH_SHORT)
                        .show()
                }
                .setNegativeButton(R.string.negative_button_name, null)
                .show()
        }
        initClientAdapter(clients)
    }

    private fun initClientAdapter(clients: List<Client>) {
        val adapter = context?.let { context -> ClientAdapter(context, clients) }
        client_list_rv.adapter = adapter
    }


}