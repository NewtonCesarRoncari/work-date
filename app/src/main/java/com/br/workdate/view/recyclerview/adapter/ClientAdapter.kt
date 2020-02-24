package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.limit
import com.br.workdate.model.Client
import kotlinx.android.synthetic.main.list_item_client.view.*
import java.util.*

class ClientAdapter(
    private val context: Context,
    private var clients: MutableList<Client>,
    var onItemClickListener: (client: Client) -> Unit = {},
    var onItemLongClickListener: (client: Client) -> Unit = {}
) : RecyclerView.Adapter<ClientAdapter.MyViewHolder>(), Filterable {

    private val clientListFull = clients.toList()

    //regionFilter
    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Client> = mutableListOf()

            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(clientListFull)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                for (client in clientListFull) {
                    if (client.name.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(client)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            clients = (results.values as List<*>).filterIsInstance<Client>() as MutableList<Client>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }
    //endregion

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
            if (client.name.isNotEmpty()) {
                clientFirstChar.text = client.name[0].toString()
            }
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
                MenuInflater(context).inflate(R.menu.base_context_menu, menu)
                menu.findItem(R.id.remove)
                    .setOnMenuItemClickListener {
                        onItemLongClickListener(clients[adapterPosition])
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}