package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.*
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.databinding.ListItemServiceBinding
import com.br.workdate.model.Service
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
                    if (service.description.toLowerCase(Locale.getDefault())
                            .contains(filterPattern)
                    ) {
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
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = DataBindingUtil.inflate<ListItemServiceBinding>(
            inflater,
            R.layout.list_item_service,
            parent,
            false
        )
        return MyViewHolder(viewDataBinding)
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(services[position])
    }

    fun remove(position: Int) {
        services.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(private val viewDataBinding: ListItemServiceBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {

        private lateinit var service: Service

        fun bind(service: Service) {
            this.service = service
            viewDataBinding.service = service.makeServiceForLayout(context)
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