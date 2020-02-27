package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.extension.formatForBrazilianHour
import com.br.workdate.extension.limit
import com.br.workdate.model.*
import com.br.workdate.view.viewmodel.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_form_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class FormScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val viewModel: ScheduleViewModel by viewModel()
    private val clientViewModel: ClientViewModel by viewModel()
    private val serviceViewModel: ServiceViewModel by viewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val arguments by navArgs<FormScheduleFragmentArgs>()
    private lateinit var date: Date
    private lateinit var hour: Date
    private val client by lazy {
        arguments.client
    }
    private val service by lazy {
        arguments.service
    }
    private val schedule by lazy {
        arguments.schedule
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_schedule, container, false)

        appComponentsViewModel.havCoponent = VisualComponents(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        client?.let { tryLoadClientFields(it) }
        service?.let { tryLoadServiceFields(it) }
        schedule?.let { tryLoadScheduleFields(it) }

        form_schedule_client_btn.setOnClickListener {
            goToSearchClientFragment()
        }
        form_schedule_service_btn.setOnClickListener {
            goToSearchServiceFragment()
        }
        form_schedule_date_btn.setOnClickListener {
            val datePicker = DatePickerHelper(
                onDataSet = { currentDate ->
                    date = currentDate
                    form_schedule_date.text = currentDate.formatForBrazilianDate()
                }
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                datePicker.show(fragmentManager, "time picker")
            }
        }

        form_schedule_hour_btn.setOnClickListener {
            val timePicker = TimePickerHelper(
                onTimeSet = { currentHour ->
                    hour = currentHour
                    form_schedule_hour.text = currentHour.formatForBrazilianHour()
                }
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                timePicker.show(fragmentManager, "time picker")
            }
        }

        form_schedule_save_btn.setOnClickListener {
            if (allIsInitialized()) {
                viewModel.insert(
                    Schedule(
                        UUID.randomUUID().toString(),
                        date,
                        hour,
                        service!!.value,
                        form_schedule_canceled_switch.isChecked,
                        form_schedule_finished_switch.isChecked,
                        form_schedule_obs.text.toString(),
                        service!!.id,
                        client!!.id
                    )
                )
                showSnackBar("Schedule saved")
            } else {
                showSnackBar("Empty fields")
            }
            navController.popBackStack()
        }
    }

    private fun showSnackBar(msg: String) {
        view?.let { view ->
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun allIsInitialized() =
        client != null && service != null && ::date.isInitialized && ::hour.isInitialized

    private fun tryLoadScheduleFields(schedule: Schedule) {
        lateinit var client: Client
        lateinit var service: Service
        clientViewModel.returnForId(schedule.clientId).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { clientReturned ->
                client = clientReturned
                tryLoadClientFields(client)
            }
        )
        serviceViewModel.returnForId(schedule.serviceId).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { serviceReturned ->
                service = serviceReturned
                tryLoadServiceFields(service)
            }
        )
        form_schedule_date.text = schedule.date.formatForBrazilianDate()
        form_schedule_hour.text = schedule.hour.formatForBrazilianHour()
        form_schedule_obs.setText(schedule.observation)
        form_schedule_canceled_switch.isChecked = schedule.canceled
        form_schedule_finished_switch.isChecked = schedule.finished
    }

    private fun tryLoadClientFields(client: Client) {
        form_schedule_client_btn.text = client.name.limit(maxCharacters = 24)
        form_schedule_client_phone.text = client.phone
    }

    private fun tryLoadServiceFields(service: Service) {
        form_schedule_service_btn.text = service.description.limit(maxCharacters = 21)
        form_schedule_service_value.text = service.value.formatForBrazilianCoin()
    }

    private fun goToSearchClientFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListClientFragment(service)
        navController.navigate(direction)
    }

    private fun goToSearchServiceFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListServiceFragment(client)
        navController.navigate(direction)
    }

}