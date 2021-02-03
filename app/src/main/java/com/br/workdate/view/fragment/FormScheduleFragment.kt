package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.br.workdate.R
import com.br.workdate.extension.*
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
    private val releaseViewModel: ReleaseViewModel by viewModel()
    private val textCanceled by lazy { form_schedule_text_canceled_switch }
    private val canceled by lazy { form_schedule_canceled_switch }
    private val textFinished by lazy { form_schedule_text_finished_switch }
    private val finished by lazy { form_schedule_finished_switch }
    private val saveBtn by lazy { form_schedule_save_btn }
    private val serviceCard by lazy { form_schedule_service_cardView }
    private val scheduleCard by lazy { form_schedule_cardView }
    private val icon by lazy { form_schedule_client_icon }
    private val char by lazy { form_schedule_client_first_char }
    private val clientName by lazy { form_schedule_client_name }
    private val clientPhone by lazy { form_schedule_client_phone }
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val arguments by navArgs<FormScheduleFragmentArgs>()
    private var value = BigDecimal.ZERO
    private lateinit var date: Date
    private lateinit var hour: Date
    private lateinit var clientId: String
    private lateinit var serviceId: String
    private lateinit var clientNameText: String
    private lateinit var serviceDescription: String
    private val client by lazy { arguments.client }
    private val service by lazy { arguments.service }
    private val schedule by lazy { arguments.schedule }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_schedule, container, false)
        appComponentsViewModel.havComponent = VisualComponents(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        client?.let { tryLoadClientFields(it) }
        service?.let { tryLoadServiceFields(it) }
        schedule?.let { tryLoadScheduleFields(it) }

        form_schedule_date_btn.setOnClickListener { initDateDialog() }
        form_schedule_hour_btn.setOnClickListener { initTimeDialog() }

        startAnimations()
        serviceCardListener()
        saveBtnListener()
    }

    private fun saveBtnListener() {
        saveBtn.setOnClickListener {
            if (scheduleIsInitialized()) {
                updateSchedule()
                updateRelease()
                showSnackBar(getString(R.string.message_schedule_updated))
            } else {
                if (allIsInitialized()) {
                    makeAndSaveReleaseBy(makeAndSaveSchedule())
                    navController.popBackStack(R.id.listScheduleFragment, false)
                    showSnackBar(getString(R.string.message_schedule_saved))
                } else {
                    showSnackBar(getString(R.string.message_empty_fields))
                }
            }
        }
    }

    private fun serviceCardListener() {
        serviceCard.setOnClickListener {
            if (!this.schedule?.id.isNullOrEmpty()) {
                goToSearchListServiceFragment()
            }
        }
    }

    private fun tryLoadScheduleFields(schedule: Schedule) {
        clientViewModel.returnForId(schedule.clientId).observe(
            viewLifecycleOwner, { clientReturned ->
                tryLoadClientFields(clientReturned)
            }
        )
        serviceViewModel.returnForId(schedule.serviceId).observe(
            viewLifecycleOwner, { serviceReturned ->
                tryLoadServiceFields(serviceReturned)
            }
        )
        date = schedule.date
        hour = schedule.hour
        form_schedule_date_btn.text = schedule.date.formatForBrazilianDate()
        form_schedule_hour_btn.text = schedule.hour.formatForBrazilianHour()
        form_schedule_obs.setText(schedule.observation)
        canceled.isChecked = schedule.canceled
        finished.isChecked = schedule.finished
    }

    private fun tryLoadClientFields(client: Client) {
        clientId = client.id
        clientNameText = client.name
        char.text = client.name[0].toString()
        clientName.text = client.name.limit(maxCharacters = 24)
        clientPhone.text = client.phone
    }

    private fun tryLoadServiceFields(service: Service) {
        serviceId = service.id
        serviceDescription = service.description
        value = service.value
        form_schedule_service_description.text = service.description.limit(maxCharacters = 28)
        form_schedule_service_value.text = context?.let { service.value.formatCoin(it) }
    }

    private fun startAnimations() {
        initTtbAnimation()
        initStbAnimation()
        initBttAnimation()
    }

    private fun initTimeDialog() {
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

    private fun goToSearchListServiceFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListServiceFragment(
                schedule?.id?.let { id ->
                    makeSchedule(id)
                })
        navController.navigate(direction)
    }

    private fun initDateDialog() {
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

    private fun initBttAnimation() {
        val btt by lazy { AnimationUtils.loadAnimation(context, R.anim.btt) }
        val btt2 by lazy { AnimationUtils.loadAnimation(context, R.anim.btt2) }
        val btt3 by lazy { AnimationUtils.loadAnimation(context, R.anim.btt3) }

        textCanceled.startAnimation(btt)
        canceled.startAnimation(btt)
        textFinished.startAnimation(btt2)
        finished.startAnimation(btt2)
        saveBtn.startAnimation(btt3)
    }

    private fun initStbAnimation() {
        val stb by lazy { AnimationUtils.loadAnimation(context, R.anim.stb) }

        serviceCard.startAnimation(stb)
        scheduleCard.startAnimation(stb)
    }

    private fun initTtbAnimation() {
        val ttb by lazy { AnimationUtils.loadAnimation(context, R.anim.ttb) }

        icon.startAnimation(ttb)
        char.startAnimation(ttb)
        clientName.startAnimation(ttb)
        clientPhone.startAnimation(ttb)
    }

    private fun makeAndSaveSchedule(): Schedule {
        val schedule = makeSchedule()
        viewModel.insert(schedule)
        return schedule
    }

    private fun updateRelease() {
        schedule?.id?.let { scheduleId ->
            releaseViewModel.findReleaseIdByScheduleId(scheduleId)
                .observe(viewLifecycleOwner, { releaseId ->
                    val schedule = makeSchedule(scheduleId)
                    releaseViewModel.update(
                        Release(
                            schedule, releaseId,
                            viewModel.checkFinished(schedule.finished)
                        )
                    )
                    navController.popBackStack(R.id.listScheduleFragment, false)
                })
        }
    }

    private fun updateSchedule() {
        schedule?.let { schedule ->
            viewModel.update(makeSchedule(schedule.id))
        }
    }

    private fun makeSchedule(id: String = ""): Schedule {
        return Schedule(
            id.returnUUID(),
            clientNameText,
            serviceDescription,
            date,
            hour,
            value,
            canceled.isChecked,
            finished.isChecked,
            form_schedule_obs.text.toString().trim(),
            serviceId,
            clientId
        )
    }

    private fun makeAndSaveReleaseBy(schedule: Schedule) {
        val release =
            Release(schedule = schedule, situation = viewModel.checkFinished(schedule.finished))
        releaseViewModel.insert(release)
    }

    private fun showSnackBar(msg: String) {
        view?.let { view ->
            Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun allIsInitialized() =
        client != null && service != null && ::date.isInitialized && ::hour.isInitialized

    private fun scheduleIsInitialized() = schedule != null
}