package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.extension.formatForBrazilianHour
import com.br.workdate.model.Release
import kotlinx.android.synthetic.main.list_item_release.view.*

class ReleaseAdapter(
    private val context: Context,
    private val releases: MutableList<Release>
) : RecyclerView.Adapter<ReleaseAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.list_item_release,
            parent,
            false
        )
        return MyViewHolder(view)
    }

    override fun getItemCount() = releases.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(releases[position])
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val client by lazy { itemView.release_client }
        private val service by lazy { itemView.release_service }
        private val value by lazy { itemView.release_value }
        private val date by lazy { itemView.release_date }
        private val hour by lazy { itemView.release_hour }

        fun bind(release: Release) {
            client.text = release.clientName
            service.text = release.serviceDescription
            value.text = release.value.formatForBrazilianCoin()
            date.text = release.date.formatForBrazilianDate()
            hour.text = release.hour.formatForBrazilianHour()
        }
    }
}