package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.limit
import com.br.workdate.model.Client
import kotlinx.android.synthetic.main.list_item_client.view.*

class ClientAdapter(
    private val context: Context,
    private val clients: MutableList<Client>,
    var onItemClickListener: (client: Client) -> Unit = {},
    var onItemLongClickListener: (client: Client) -> Unit = {}
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
        private val limitForChar = 26
        private val clientName = itemView.list_item_client_name
        private val clientAddress = itemView.list_item_client_address
        private val clientPhone = itemView.list_item_client_phone
        private val clientFirstChar = itemView.list_item_client_first_char

        fun bind(client: Client) {
            this.client = client
            clientFirstChar.text = client.name[0].toString()
            clientName.text = client.name.limit(limitForChar)
            clientAddress.text = client.address.limit(limitForChar)
            clientPhone.text = client.phone
        }

        init {
            itemView.setOnClickListener {
                if (::client.isInitialized) {
                    onItemClickListener(client)
                }
            }
            initContextMenu(itemView, onItemLongClickListener)
        }

        private fun initContextMenu(
            itemView: View,
            onItemLongClickListener: (client: Client) -> Unit
        ) {
            itemView.setOnCreateContextMenuListener { menu: ContextMenu,
                                                      _: View?,
                                                      _: ContextMenuInfo? ->
                MenuInflater(context).inflate(R.menu.list_client_context_menu, menu)
                menu.findItem(R.id.menu_list_client_remove)
                    .setOnMenuItemClickListener {
                        onItemLongClickListener(clients[adapterPosition])
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}