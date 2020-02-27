package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.model.Schedule
import kotlinx.android.synthetic.main.list_item_schedule.view.*

class ScheduleAdapter(
    private val context: Context,
    private val schedules: MutableList<Schedule>,
    var onItemClickListener: (schedule: Schedule) -> Unit = {},
    var onItemLongClickListener: (schedule: Schedule) -> Unit = {},
    var loadFieldClientName: (clientId: String, TextView) -> Unit,
    var loadFieldServiceDescription: (serviceId: String, TextView) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_schedule, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = schedules.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    fun remove(position: Int) {
        schedules.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private lateinit var schedule: Schedule
        private val scheduleId by lazy { itemView.item_schedule_id }
        private val hour by lazy { itemView.item_schedule_hour }
        private val value by lazy { itemView.item_schedule_value }
        private val scheduleClient by lazy { itemView.item_schedule_client }
        private val scheduleService by lazy { itemView.item_schedule_service }


        fun bind(schedule: Schedule) {
            this.schedule = schedule
            scheduleId.text = schedule.id
            hour.text = schedule.hour.formatForBrazilianDate()
            value.text = schedule.value.formatForBrazilianCoin()
            loadFieldClientName(schedule.clientId, scheduleClient)
            loadFieldServiceDescription(schedule.serviceId, scheduleService)
        }

        init {
            itemView.setOnClickListener {
                if (::schedule.isInitialized) {
                    onItemClickListener(schedule)
                }
            }
            initContextMenu(itemView, onItemLongClickListener)
        }

        private fun initContextMenu(
            itemView: View,
            onItemLongClickListener: (schedule: Schedule) -> Unit
        ) {
            itemView.setOnCreateContextMenuListener { menu, _, _ ->
                MenuInflater(context).inflate(R.menu.base_context_menu, menu)
                menu.findItem(R.id.remove)
                    .setOnMenuItemClickListener {
                        onItemLongClickListener(schedules[adapterPosition])
                        remove(adapterPosition)
                        true
                    }
            }
        }

    }
}