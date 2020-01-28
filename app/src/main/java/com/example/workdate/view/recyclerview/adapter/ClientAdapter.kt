package com.example.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workdate.R
import com.example.workdate.model.Client
import kotlinx.android.synthetic.main.list_item_client.view.*

class ClientAdapter(
    private val context: Context,
    private val clients: List<Client>
) : RecyclerView.Adapter<ClientAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_client, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = clients.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val clientName: TextView = itemView.list_item_client_name

        fun bind(client: Client) {
            clientName.text = client.name
        }

    }
}