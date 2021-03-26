package com.br.workdate.view.fragment

import android.animation.Animator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.airbnb.lottie.LottieAnimationView
import com.br.workdate.R
import com.br.workdate.extension.formatForTextMonth
import com.br.workdate.extension.getWindow
import com.br.workdate.extension.limit
import com.br.workdate.model.Release
import com.br.workdate.model.Schedule
import com.br.workdate.view.dialog.FilterDialog
import com.br.workdate.view.recyclerview.adapter.ScheduleAdapter
import com.br.workdate.view.viewmodel.*
import kotlinx.android.synthetic.main.fragment_list_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.properties.Delegates

class ListScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val viewModel: ScheduleViewModel by viewModel()
    private val clientViewModel: ClientViewModel by viewModel()
    private val serviceViewModel: ServiceViewModel by viewModel()
    private val filterViewModel: FilterViewModel by viewModel()
    private val loginViewModel: LoginViewModel by sharedViewModel()
    private val releaseViewModel: ReleaseViewModel by viewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private var firstOfMonth by Delegates.notNull<Long>()
    private var lastOfMonth by Delegates.notNull<Long>()
    private var month = 1
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
        logo_app_animation.setAnimation("anim/workdate_app.json")

        val ttb = AnimationUtils.loadAnimation(requireContext(), R.anim.ttb)
        val cardView = schedule_resume_cardView
        cardView.startAnimation(ttb)
        month = getActualMonth()

        initViews()
        initDonutAnimation()
        getMonthForFilter()
        initResume()
        checkStateLogin(view)
        viewModel.listSchedulesInMonthNoFinished(firstOfMonth, lastOfMonth)
            .observe(viewLifecycleOwner, { scheduleList ->
                ifEmptyPlayAnimation(scheduleList)
                initAdapter(scheduleList)
            })

        setHasOptionsMenu(true)
    }

    private fun getMonthForFilter() {

        val gc: Calendar = GregorianCalendar()
        gc[Calendar.MONTH] = month
        gc[Calendar.DAY_OF_MONTH] = 1
        gc[Calendar.HOUR_OF_DAY] = 0
        gc[Calendar.MINUTE] = 0
        gc[Calendar.SECOND] = 0
        gc[Calendar.MILLISECOND] = 0
        val monthStart = gc.time
        gc.add(Calendar.MONTH, 1)
        gc.add(Calendar.DAY_OF_MONTH, -1)
        val monthEnd = gc.time

        firstOfMonth = monthStart.time
        lastOfMonth = monthEnd.time
    }

    private fun getActualMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH)
    }

    private fun initViews() {
        fragment_list_text_month.text = Date().formatForTextMonth()
        new_schedule.setOnClickListener {
            goToSearchClientFragment()
        }
        fragment_list_right_arrow.setOnClickListener {
            if (month in 0..10)
                month += 1
            updateAdapterWithMonthBusiness()
        }
        fragment_list_left_arrow.setOnClickListener {
            if (month in 1..11)
                month -= 1
            updateAdapterWithMonthBusiness()
        }
    }

    private fun updateAdapterWithMonthBusiness() {
        getMonthForFilter()
        viewModel.listSchedulesInMonthNoFinished(firstOfMonth, lastOfMonth)
            .observe(viewLifecycleOwner, { schedules ->
                ifEmptyPlayAnimation(schedules)
                initAdapter(schedules)
            })
        fragment_list_text_month.text = Date(firstOfMonth).formatForTextMonth()
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

    @SuppressLint("RestrictedApi")
    private fun checkStateLogin(view: View) {
        if (!loginViewModel.isLogged()) {
            schedule_list_animation.visibility = GONE
            initAnimation(logo_app_animation)
            new_schedule.visibility = GONE
            logo_app_animation.addAnimatorListener(animatorListener(view))
        } else {
            logo_app_animation.visibility = GONE
        }
    }


    private fun initDonutAnimation() {
        fragment_list_ui_chart.donutColors = ResumeScheduleView.myDonutColors
        fragment_list_ui_chart.animation.duration = ResumeScheduleView.durationDonutAnimation
        fragment_list_ui_chart.animate(ResumeScheduleView.donutSet)
    }

    private fun initResume() {
        lateinit var resumeSchedule: ResumeScheduleView
        val view = activity?.window?.decorView
        viewModel.listAll()
            .observe(viewLifecycleOwner, { schedules ->
                resumeSchedule = view?.let { view -> ResumeScheduleView(view, schedules) }!!
                resumeSchedule.update()
                initDonutAnimation()
            })
    }

    private fun animatorListener(view: View): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                schedule_list_animation.visibility = GONE
            }

            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animator) {
                initAnimation(schedule_list_animation)
                new_schedule.visibility = VISIBLE
                logo_app_animation.visibility = GONE

                val (width: Int, height: Int) = getWindow(activity)
                loginViewModel.initTutorial(TutorialOfListSchedule(), activity, view, width, height)

                loginViewModel.login()
            }

            override fun onAnimationCancel(animation: Animator) {} //method implemented in delegate
            override fun onAnimationRepeat(animation: Animator) {} //method implemented in delegate
        }
    }

    private fun initAdapter(scheduleList: MutableList<Schedule>) {
        adapter = ScheduleAdapter(
            requireContext(),
            scheduleList,
            loadFieldClientName = { clientId: String,
                                    fieldClientName: TextView ->
                clientViewModel.getNameForId(clientId)
                    .observe(viewLifecycleOwner, { clientName ->
                        fieldClientName.text = clientName.limit(28)
                    })
            },
            loadFieldServiceDescription = { serviceId: String,
                                            fieldServiceDescription: TextView ->
                serviceViewModel.returnDescriptionForId(serviceId).observe(
                    viewLifecycleOwner, { serviceDescription ->
                        fieldServiceDescription.text = serviceDescription.limit(17)
                    }
                )
            },
            setScheduleFinished = { schedule ->
                val scheduleToSave = Schedule(
                    schedule.id,
                    schedule.clientName,
                    schedule.serviceDescription,
                    schedule.date,
                    schedule.hour,
                    schedule.value,
                    schedule.canceled,
                    true,
                    schedule.observation,
                    schedule.serviceId,
                    schedule.clientId
                )
                viewModel.update(scheduleToSave)
                releaseViewModel.findReleaseIdByScheduleId(scheduleToSave.id)
                    .observe(viewLifecycleOwner, { releaseId ->
                        releaseViewModel.update(
                            Release(
                                scheduleToSave,
                                releaseId,
                                viewModel.checkFinished(scheduleToSave.finished)
                            )
                        )
                    })
            }
        )
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
        activity?.let { activity ->
            FilterDialog(
                requireContext(),
                activity,
                FilterOfSchedule(),
                loadClientNames = { clientAutoComplete ->
                    filterViewModel.returnAllClientNames()
                        .observe(viewLifecycleOwner, { names ->
                            val clientAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                names
                            )
                            clientAutoComplete.setAdapter(clientAdapter)
                        })
                },
                loadServiceDescriptions = { serviceAutoComplete ->
                    filterViewModel.returnAllServicesDescriptions()
                        .observe(viewLifecycleOwner, { descriptions ->
                            val serviceAdapter = ArrayAdapter(
                                requireContext(),
                                R.layout.support_simple_spinner_dropdown_item,
                                descriptions
                            )
                            serviceAutoComplete.setAdapter(serviceAdapter)
                        })
                },
                returnQuery = { query ->
                    viewModel.findScheduleFilter(query)
                        .observe(viewLifecycleOwner, { scheduleList ->
                            ifEmptyPlayAnimation(scheduleList)
                            initAdapter(scheduleList)
                            fragment_list_text_month.text = "-"
                        })
                }
            ).showFilterDialog()
        }
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Schedule>) {
        if (mutableList.isEmpty() && loginViewModel.isLogged()) {
            initAnimation(schedule_list_animation)
        } else {
            schedule_list_animation.visibility = GONE
        }
    }

    private fun initAnimation(animation: LottieAnimationView) {
        with(animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }
}