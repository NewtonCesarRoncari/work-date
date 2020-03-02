package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import com.br.workdate.extension.limit
import com.br.workdate.model.Schedule
import com.br.workdate.view.recyclerview.adapter.ScheduleAdapter
import com.br.workdate.view.viewmodel.*
import kotlinx.android.synthetic.main.fragment_list_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ListScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val viewModel: ScheduleViewModel by viewModel()
    private val clientViewModel: ClientViewModel by viewModel()
    private val serviceViewModel: ServiceViewModel by viewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_schedule, container, false)

        appComponentsViewModel.havCoponent = VisualComponents(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        schedule_list_animation.setAnimation("anim/list_empty.json")

        new_schedule.setOnClickListener {
            goToFormScheduleFragment()
        }
        viewModel.listAll().observe(viewLifecycleOwner, Observer { scheduleList ->
            ifEmptyPlayAnimation(scheduleList)
            initAdapter(scheduleList)
        })
        setHasOptionsMenu(true)
    }

    private fun initAdapter(scheduleList: MutableList<Schedule>) {
        adapter = context?.let { context ->
            ScheduleAdapter(
                context,
                scheduleList,
                loadFieldClientName = { clientId: String,
                                        fieldClientName: TextView ->
                    clientViewModel.getNameForId(clientId)
                        .observe(viewLifecycleOwner, Observer { clientName ->
                            fieldClientName.text = clientName.limit(24)
                        })
                },
                loadFieldServiceDescription = { serviceId: String,
                                                fieldServiceDescription: TextView ->
                    serviceViewModel.returnDescriptionForId(serviceId).observe(
                        viewLifecycleOwner,
                        Observer { serviceDescription ->
                            fieldServiceDescription.text = serviceDescription.limit(24)
                        }
                    )
                }
            )
        }!!
        schedule_list_rv.adapter = adapter
        adapter.onItemClickListener = { schedule ->
            goToFormScheduleFragment(schedule)
        }
        adapter.onItemLongClickListener = { schedule ->
            viewModel.remove(schedule)
        }
    }

    private fun goToFormScheduleFragment(schedule: Schedule) {
        val direction = ListScheduleFragmentDirections
            .actionListScheduleFragmentToFormScheduleFragment(null, null, schedule)
        navController.navigate(direction)
    }

    private fun goToFormScheduleFragment() {
        val direction = ListScheduleFragmentDirections
            .actionListScheduleFragmentToFormScheduleFragment(null, null, null)
        navController.navigate(direction)
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Schedule>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            schedule_list_animation.visibility = View.GONE
        }
    }

    private fun initAnimation() {
        with(schedule_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = View.VISIBLE
            playAnimation()
        }
    }
}