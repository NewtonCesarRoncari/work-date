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
import java.math.BigDecimal
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
    private lateinit var value: BigDecimal
    private lateinit var clientId: String
    private lateinit var serviceId: String
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

        form_schedule_service_cardView.setOnClickListener {
            val direction = FormScheduleFragmentDirections
                .actionFormScheduleFragmentToSearchListServiceFragment(
                    schedule?.id?.let { id ->
                        makeSchedule(id)
                    })
            navController.navigate(direction)
        }

        form_schedule_date_btn.setOnClickListener {
            val datePicker = DatePickerHelper(
                onDataSet = { currentDate ->
                    date = currentDate
                    form_schedule_date_btn.text = currentDate.formatForBrazilianDate()
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
                    form_schedule_hour_btn.text = currentHour.formatForBrazilianHour()
                }
            )
            activity?.supportFragmentManager?.let { fragmentManager ->
                timePicker.show(fragmentManager, "time picker")
            }
        }

        form_schedule_save_btn.setOnClickListener {
            if (scheduleIsInitialized()) {
                makeAndUpdateSchedule()
                navController.popBackStack(R.id.listScheduleFragment, false)
                showSnackBar("Schedule updated")
            } else {
                if (allIsInitialized()) {
                    makeAndSaveSchedule()
                    navController.popBackStack(R.id.listScheduleFragment, false)
                    showSnackBar("Schedule saved")
                } else {
                    showSnackBar("Empty fields")
                }
            }
        }
    }

    private fun makeAndUpdateSchedule() {
        schedule?.let { schedule1 ->
            viewModel.update(
                makeSchedule(schedule1.id)
            )
        }
    }

    private fun makeSchedule(id: String): Schedule {
        return Schedule(
            id,
            date,
            hour,
            value,
            form_schedule_canceled_switch.isChecked,
            form_schedule_finished_switch.isChecked,
            form_schedule_obs.text.toString().trim(),
            serviceId,
            clientId
        )
    }

    private fun makeAndSaveSchedule() {
        viewModel.insert(
            Schedule(
                UUID.randomUUID().toString(),
                date,
                hour,
                service!!.value,
                form_schedule_canceled_switch.isChecked,
                form_schedule_finished_switch.isChecked,
                form_schedule_obs.text.toString().trim(),
                service!!.id,
                client!!.id
            )
        )
    }

    private fun showSnackBar(msg: String) {
        view?.let { view ->
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun allIsInitialized() =
        client != null && service != null && ::date.isInitialized && ::hour.isInitialized

    private fun scheduleIsInitialized() = schedule != null

    private fun tryLoadScheduleFields(schedule: Schedule) {
        clientViewModel.returnForId(schedule.clientId).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { clientReturned ->
                tryLoadClientFields(clientReturned)
            }
        )
        serviceViewModel.returnForId(schedule.serviceId).observe(
            viewLifecycleOwner,
            androidx.lifecycle.Observer { serviceReturned ->
                tryLoadServiceFields(serviceReturned)
            }
        )
        date = schedule.date
        hour = schedule.hour
        form_schedule_date_btn.text = schedule.date.formatForBrazilianDate()
        form_schedule_hour_btn.text = schedule.hour.formatForBrazilianHour()
        form_schedule_obs.setText(schedule.observation)
        form_schedule_canceled_switch.isChecked = schedule.canceled
        form_schedule_finished_switch.isChecked = schedule.finished
    }

    private fun tryLoadClientFields(client: Client) {
        clientId = client.id
        form_schedule_client_first_char.text = client.name[0].toString()
        form_schedule_client_name.text = client.name.limit(maxCharacters = 24)
        form_schedule_client_phone.text = client.phone
    }

    private fun tryLoadServiceFields(service: Service) {
        serviceId = service.id
        value = service.value
        form_schedule_service_description.text = service.description.limit(maxCharacters = 28)
        form_schedule_service_value.text = service.value.formatForBrazilianCoin()
    }

}