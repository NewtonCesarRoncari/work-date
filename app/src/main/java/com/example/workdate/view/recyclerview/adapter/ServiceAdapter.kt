package com.example.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.workdate.R
import com.example.workdate.extension.formatForBrazilianCoin
import com.example.workdate.extension.limit
import com.example.workdate.model.Service
import kotlinx.android.synthetic.main.list_item_service.view.*

class ServiceAdapter(
    private val context: Context,
    private val services: List<Service>,
    var onItemClickListener: (service: Service) -> Unit = {}
) : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_service, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = services.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(services[position])
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
        }

    }
}