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
import com.br.workdate.databinding.FragmentFormScheduleBinding
import com.br.workdate.extension.*
import com.br.workdate.model.*
import com.br.workdate.view.viewmodel.*
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal
import java.util.*

class FormScheduleFragment : Fragment() {

    private val binding by viewBinding(FragmentFormScheduleBinding::bind)
    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val viewModel: ScheduleViewModel by viewModel()
    private val clientViewModel: ClientViewModel by viewModel()
    private val serviceViewModel: ServiceViewModel by viewModel()
    private val releaseViewModel: ReleaseViewModel by viewModel()
    private val loginViewModel: LoginViewModel by sharedViewModel()
    private val textCanceled by lazy { binding.formScheduleTextCanceledSwitch }
    private val canceled by lazy { binding.formScheduleCanceledSwitch }
    private val textFinished by lazy { binding.formScheduleTextFinishedSwitch }
    private val finished by lazy { binding.formScheduleFinishedSwitch }
    private val saveBtn by lazy { binding.formScheduleSaveBtn }
    private val serviceCard by lazy { binding.formScheduleServiceCardView }
    private val scheduleCard by lazy { binding.formScheduleServiceCardView }
    private val icon by lazy { binding.formScheduleClientIcon }
    private val char by lazy { binding.formScheduleClientFirstChar }
    private val clientName by lazy { binding.formScheduleClientName }
    private val clientPhone by lazy { binding.formScheduleClientPhone }
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
    ): View {
        val view = FragmentFormScheduleBinding.inflate(inflater).root
        appComponentsViewModel.havComponent = VisualComponents(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        client?.let { tryLoadClientFields(it) }
        service?.let { tryLoadServiceFields(it) }
        schedule?.let { tryLoadScheduleFields(it) }
        checkIsFirstTimeInApp(view)

        binding.formScheduleDateBtn.setOnClickListener { initDateDialog() }
        binding.formScheduleHourBtn.setOnClickListener { initTimeDialog() }

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
        binding.formScheduleDateBtn.text = schedule.date.formatForBrazilianDate()
        binding.formScheduleHourBtn.text = schedule.hour.formatForBrazilianHour()
        binding.formScheduleObs.setText(schedule.observation)
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
        binding.formScheduleServiceDescription.text = service.description.limit(maxCharacters = 28)
        binding.formScheduleServiceValue.text = context?.let { service.value.formatCoin(it) }
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
                binding.formScheduleHourBtn.text = currentHour.formatForBrazilianHour()
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
                binding.formScheduleDateBtn.text = currentDate.formatForBrazilianDate()
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
            binding.formScheduleObs.text.toString().trim(),
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

    private fun checkIsFirstTimeInApp(view: View) {
        if (loginViewModel.firstTimeInScreen(Constant.TITLE)) {
            val (width: Int, height: Int) = getWindow(activity)
            loginViewModel.initTutorial(TutorialOfFormSchedule(), activity, view, width, height)
        }
    }

    private object Constant {
        const val TITLE = "SCHEDULE_FORM_SCREEN"
    }

    private fun allIsInitialized() =
        client != null && service != null && ::date.isInitialized && ::hour.isInitialized

    private fun scheduleIsInitialized() = schedule != null
}