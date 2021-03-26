package com.br.workdate.view.recyclerview.adapter

import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_CALL
import android.content.Intent.ACTION_VIEW
import android.content.pm.PackageManager
import android.net.Uri
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import android.widget.Filter
import android.widget.Filterable
import android.widget.PopupMenu
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.databinding.ListItemClientBinding
import com.br.workdate.model.Client
import kotlinx.android.synthetic.main.list_item_client.view.*
import java.util.*


class ClientAdapter(
    private val context: Context,
    private var clients: MutableList<Client>,
    var onItemClickListener: (client: Client) -> Unit = {},
    var onItemLongClickListener: (client: Client) -> Unit = {},
    var showMessageClientNoAddress: () -> Unit = {},
    var showMessageClientNoPhone: () -> Unit = {}
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
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = DataBindingUtil.inflate<ListItemClientBinding>(
            inflater,
            R.layout.list_item_client,
            parent,
            false
        )
        return MyViewHolder(viewDataBinding)
    }

    override fun getItemCount() = clients.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    fun remove(position: Int) {
        checkHavePositionInList(position)
        clients.removeAt(position)
        notifyItemRemoved(position)
    }

    private fun checkHavePositionInList(position: Int) {
        if (position < 0 || position > clients.size) throw IndexOutOfBoundsException()
    }

    inner class MyViewHolder(private val viewDataBinding: ListItemClientBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private lateinit var client: Client
        private val btnViewOptions: AppCompatImageView by lazy { itemView.list_item_option }

        fun bind(client: Client) {
            this.client = client
            viewDataBinding.client = client.makeClientForLayout()
        }

        init {
            itemView.setOnClickListener {
                if (::client.isInitialized) {
                    onItemClickListener(client)
                }
            }
            btnViewOptions.setOnClickListener { initOptionPopup() }
            initContextMenu(itemView, onItemLongClickListener)
        }

        private fun initOptionPopup() {
            val popup = PopupMenu(context, btnViewOptions)
            popup.inflate(R.menu.list_options_item_client_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.list_item_options_call -> {
                        if (checkCallPhonePermission()) {
                            requestCallPhonePermission()
                        } else {
                            if (client.phone == "")
                                showMessageClientNoPhone() else
                            initCall(client.phone)
                        }
                    }
                    R.id.list_item_options_locate -> {
                        if (client.address == "")
                            showMessageClientNoAddress() else
                            initGoogleMaps(client.address, menuItem)
                    }
                }
                false
            }
            popup.show()
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

        private fun requestCallPhonePermission() =
            ActivityCompat.requestPermissions(
                (context as Activity?)!!,
                arrayOf(CALL_PHONE),
                123
            )

        private fun checkCallPhonePermission() =
            ActivityCompat.checkSelfPermission(
                Objects.requireNonNull(context),
                CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED

        private fun initCall(phone: String) {
            val intentCall = Intent(ACTION_CALL)
            intentCall.data = Uri.parse("tel:$phone")
            startActivity(context, intentCall, null)
        }

        private fun initGoogleMaps(address: String, menuItem: MenuItem) {
            val intentLocal = Intent(ACTION_VIEW)
            intentLocal.data = Uri.parse("geo:0,0?q=$address")
            menuItem.intent = intentLocal
        }
    }
}