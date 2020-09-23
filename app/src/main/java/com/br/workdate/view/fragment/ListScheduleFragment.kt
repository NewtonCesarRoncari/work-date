package com.br.workdate.view.fragment

import android.animation.Animator
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
import com.br.workdate.extension.limit
import com.br.workdate.model.Release
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
    private val loginViewModel: LoginViewModel by viewModel()
    private val releaseViewModel: ReleaseViewModel by viewModel()
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
        logo_app_animation.setAnimation("anim/workdate_app.json")

        val ttb = AnimationUtils.loadAnimation(requireContext(), R.anim.ttb)
        val cardView = schedule_resume_cardView
        cardView.startAnimation(ttb)

        initDonutAnimation()
        initResume()
        checkStateLogin()
        new_schedule.setOnClickListener {
            goToSearchClientFragment()
        }
        viewModel.listAllNoFinished().observe(viewLifecycleOwner, { scheduleList ->
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

    private fun checkStateLogin() {
        if (!loginViewModel.isLogged()) {
            schedule_list_animation.visibility = GONE
            initAnimation(logo_app_animation)
            new_schedule.visibility = GONE
            logo_app_animation.addAnimatorListener(animatorListener())
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
        viewModel.listAll().observe(viewLifecycleOwner, { schedules ->
            resumeSchedule = view?.let { view -> ResumeScheduleView(view, schedules) }!!
            resumeSchedule.update()
        })
    }

    private fun animatorListener(): Animator.AnimatorListener {
        return object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                schedule_list_animation.visibility = GONE
            }

            override fun onAnimationEnd(animation: Animator) {
                initAnimation(schedule_list_animation)
                new_schedule.visibility = VISIBLE
                logo_app_animation.visibility = GONE
                loginViewModel.login()
            }

            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        }
    }

    private fun initAdapter(scheduleList: MutableList<Schedule>) {
        adapter = context?.let { context ->
            ScheduleAdapter(
                context,
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
                        })
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
                        finished = true,
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
                    FilterOfSchedule(),
                    loadClientNames = { clientAutoComplete ->
                        filterViewModel.returnAllClientNames()
                            .observe(viewLifecycleOwner, { names ->
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
                            .observe(viewLifecycleOwner, { descriptions ->
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
                            .observe(viewLifecycleOwner, { scheduleList ->
                                ifEmptyPlayAnimation(scheduleList)
                                initAdapter(scheduleList)
                            })
                    }
                ).showFilterDialog()
            }
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