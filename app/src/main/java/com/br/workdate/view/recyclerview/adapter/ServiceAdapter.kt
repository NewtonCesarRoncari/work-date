package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.limit
import com.br.workdate.model.Service
import kotlinx.android.synthetic.main.list_item_service.view.*
import java.util.*

class ServiceAdapter(
    private val context: Context,
    private var services: MutableList<Service>,
    var onItemClickListener: (service: Service) -> Unit = {},
    var onItemLongClickListener: (service: Service) -> Unit = {}
) : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>(), Filterable {

    private val serviceFullList = services.toList()

    //regionFilter
    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList: MutableList<Service> = mutableListOf()

            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(serviceFullList)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.getDefault()).trim()

                for (service in serviceFullList) {
                    if (service.description.toLowerCase(Locale.getDefault()).contains(filterPattern)) {
                        filteredList.add(service)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            results.count = filteredList.size

            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            services =
                (results.values as List<*>).filterIsInstance<Service>() as MutableList<Service>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }
    //endregion

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
        private val serviceFirstChar: TextView = itemView.list_item_service_first_char

        fun bind(service: Service) {
            this.service = service
            if (service.description.isNotEmpty()) {
                serviceFirstChar.text = service.description[0].toString()
            }
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
                MenuInflater(context).inflate(R.menu.base_context_menu, menu)
                menu.findItem(R.id.remove)
                    .setOnMenuItemClickListener {
                        onItemLongClickListener(services[adapterPosition])
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}