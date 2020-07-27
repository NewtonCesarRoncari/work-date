package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import com.br.workdate.extension.limit
import com.br.workdate.model.Schedule
import com.br.workdate.view.dialog.FilterDialog
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
    private val filterViewModel: FilterViewModel by viewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private lateinit var adapter: ScheduleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_schedule, container, false)

        appComponentsViewModel.havComponent = VisualComponents(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        schedule_list_animation.setAnimation("anim/list_empty.json")

        new_schedule.setOnClickListener {
            goToSearchClientFragment()
        }
        viewModel.listAll().observe(viewLifecycleOwner, Observer { scheduleList ->
            ifEmptyPlayAnimation(scheduleList)
            initAdapter(scheduleList)
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.base_filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            initFilterDialog()
        }
        return super.onOptionsItemSelected(item)
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
                            fieldClientName.text = clientName.limit(28)
                        })
                },
                loadFieldServiceDescription = { serviceId: String,
                                                fieldServiceDescription: TextView ->
                    serviceViewModel.returnDescriptionForId(serviceId).observe(
                        viewLifecycleOwner,
                        Observer { serviceDescription ->
                            fieldServiceDescription.text = serviceDescription.limit(17)
                        })
                })
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
            .actionListScheduleFragmentToFormScheduleFragment(schedule = schedule)
        navController.navigate(direction)
    }

    private fun goToSearchClientFragment() {
        val direction = ListScheduleFragmentDirections
            .actionListScheduleFragmentToSearchListClientFragment()
        navController.navigate(direction)
    }


    private fun initFilterDialog() {
        context?.let { context ->
            activity?.let { activity ->
                FilterDialog(
                    context,
                    activity,
                    "SCHEDULE",
                    loadClientNames = { clientAutoComplete ->
                        filterViewModel.returnAllClientNames()
                            .observe(viewLifecycleOwner, Observer { names ->
                                val clientAdapter = ArrayAdapter(
                                    context,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    names
                                )
                                clientAutoComplete.setAdapter(clientAdapter)
                            })
                    },
                    loadServiceDescriptions = { serviceAutoComplete ->
                        filterViewModel.returnAllServicesDescriptions()
                            .observe(viewLifecycleOwner, Observer { descriptions ->
                                val serviceAdapter = ArrayAdapter(
                                    context,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    descriptions
                                )
                                serviceAutoComplete.setAdapter(serviceAdapter)
                            })
                    },
                    returnQuery = { query ->
                        viewModel.findScheduleFilter(query)
                            .observe(viewLifecycleOwner, Observer { scheduleList ->
                                ifEmptyPlayAnimation(scheduleList)
                                initAdapter(scheduleList)
                            })
                    }
                ).showFilterDialog()
            }
        }
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