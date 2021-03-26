package com.br.workdate.view.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.br.workdate.R
import com.br.workdate.databinding.ListItemScheduleBinding
import com.br.workdate.model.Schedule
import kotlinx.android.synthetic.main.list_item_schedule.view.*

class ScheduleAdapter(
    private val context: Context,
    private val schedules: MutableList<Schedule>,
    var onItemClickListener: (schedule: Schedule) -> Unit = {},
    var onItemLongClickListener: (schedule: Schedule) -> Unit = {},
    var loadFieldClientName: (clientId: String, TextView) -> Unit,
    var loadFieldServiceDescription: (serviceId: String, TextView) -> Unit,
    var setScheduleFinished: (schedule: Schedule) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val viewDataBinding = DataBindingUtil.inflate<ListItemScheduleBinding>(
            inflater,
            R.layout.list_item_schedule,
            parent,
            false
        )
        return MyViewHolder(viewDataBinding)
    }

    override fun getItemCount() = schedules.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(schedules[position])
    }

    fun remove(position: Int) {
        schedules.removeAt(position)
        notifyItemRemoved(position)
    }

    inner class MyViewHolder(private val viewBindingUtil: ListItemScheduleBinding) :
        RecyclerView.ViewHolder(viewBindingUtil.root) {

        private lateinit var schedule: Schedule
        private val value by lazy { itemView.item_schedule_value }
        private val scheduleClient by lazy { itemView.item_schedule_client }
        private val scheduleService by lazy { itemView.item_schedule_service }
        private val btnViewOptions: AppCompatImageView by lazy { itemView.list_item_option }


        fun bind(schedule: Schedule) {
            this.schedule = schedule
            viewBindingUtil.schedule = schedule.makeScheduleForLayoutAdapter(context)
            loadFieldClientName(schedule.clientId, scheduleClient)
            loadFieldServiceDescription(schedule.serviceId, scheduleService)
        }

        init {
            itemView.setOnClickListener {
                if (::schedule.isInitialized) {
                    onItemClickListener(schedule)
                }
            }
            btnViewOptions.setOnClickListener { initOptionPopup() }
            initContextMenu(itemView, onItemLongClickListener)
        }

        private fun initOptionPopup() {
            val popup = PopupMenu(context, btnViewOptions)
            popup.inflate(R.menu.list_options_item_schedule_menu)
            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.list_item_options_finish -> {
                        setScheduleFinished(schedule)
                    }
                }
                false
            }
            popup.show()
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