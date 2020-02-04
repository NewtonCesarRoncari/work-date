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
                tel = "(37) 9 9123-5555",
                adress = "Rua Sebastião Alves de Oliveira, 293"
            ), Client(
                name = "Newton Cesar ",
                tel = "(37) 9 9123-5555",
                adress = "Rua Sebastião Alves de Oliveira, 293"
            ), Client(
                name = "Newton Cesar",
                tel = "(37) 9 9123-5555",
                adress = "Rua Sebastião Alves de Oliveira, 293"
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
                .setTitle(R.string.title_form_new_client).setView(viewCreated).show()
        }
        initClientAdapter(clients)
    }

    private fun initClientAdapter(clients: List<Client>) {
        val adapter = context?.let { context -> ClientAdapter(context, clients) }
        client_list_rv.adapter = adapter
    }


}