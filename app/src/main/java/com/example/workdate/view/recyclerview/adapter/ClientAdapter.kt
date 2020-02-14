package com.example.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workdate.R
import com.example.workdate.extension.limit
import com.example.workdate.model.Client
import kotlinx.android.synthetic.main.list_item_client.view.*

class ClientAdapter(
    private val context: Context,
    private val clients: MutableList<Client>,
    var onItemClickListener: (client: Client) -> Unit = {}
) : RecyclerView.Adapter<ClientAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_client, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = clients.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    fun remove(position: Int) {
        clients.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var client: Client
        private val limitForName = 26
        private val clientName: TextView = itemView.list_item_client_name

        fun bind(client: Client) {
            this.client = client
            clientName.text = client.name.limit(limitForName)
        }

        init {
            itemView.setOnClickListener {
                if (::client.isInitialized) {
                    onItemClickListener(client)
                }
            }
            initContextMenu(itemView)
        }

        private fun initContextMenu(itemView: View) {
            itemView.setOnCreateContextMenuListener { menu: ContextMenu,
                                                      _: View?,
                                                      _: ContextMenuInfo? ->
                MenuInflater(context).inflate(R.menu.list_client_context_menu, menu)
                menu.findItem(R.id.menu_list_client_remove)
                    .setOnMenuItemClickListener {
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}