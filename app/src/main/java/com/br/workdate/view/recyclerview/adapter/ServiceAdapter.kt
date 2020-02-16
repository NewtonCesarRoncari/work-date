package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.limit
import com.br.workdate.model.Service
import kotlinx.android.synthetic.main.list_item_service.view.*

class ServiceAdapter(
    private val context: Context,
    private val services: MutableList<Service>,
    var onItemClickListener: (service: Service) -> Unit = {},
    var onItemLongClickListener: (service: Service) -> Unit = {}
) : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(services[position])
    }

    fun remove(position: Int) {
        services.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var service: Service
        private val limitForDescription = 26
        private val serviceDescription: TextView = itemView.list_item_service_description
        private val serviceValue: TextView = itemView.list_item_service_value

        fun bind(service: Service) {
            this.service = service
            serviceDescription.text = service.description.limit(limitForDescription)
            serviceValue.text = service.value.formatForBrazilianCoin()
        }

        init {
            itemView.setOnClickListener {
                if (::service.isInitialized) {
                    onItemClickListener(service)
                }
            }
            initContextMenu(itemView, onItemLongClickListener)
        }

        private fun initContextMenu(
            itemView: View,
            onItemLongClickListener: (service: Service) -> Unit
        ) {
            itemView.setOnCreateContextMenuListener { menu: ContextMenu,
                                                      _: View?,
                                                      _: ContextMenu.ContextMenuInfo? ->
                MenuInflater(context).inflate(R.menu.list_service_context_menu, menu)
                menu.findItem(R.id.menu_list_service_remove)
                    .setOnMenuItemClickListener {
                        onItemLongClickListener(services[adapterPosition])
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}