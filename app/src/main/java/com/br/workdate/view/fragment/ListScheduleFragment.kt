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
import com.br.workdate.databinding.FragmentListScheduleBinding
import com.br.workdate.extension.formatForTextMonth
import com.br.workdate.extension.getWindow
import com.br.workdate.extension.limit
import com.br.workdate.model.Release
import com.br.workdate.model.Schedule
import com.br.workdate.view.dialog.FilterDialog
import com.br.workdate.view.recyclerview.adapter.ScheduleAdapter
import com.br.workdate.view.viewmodel.*
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.android.synthetic.main.fragment_list_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.properties.Delegates

class ListScheduleFragment : Fragment() {

    private val binding by viewBinding(FragmentListScheduleBinding::bind)
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
    ): View {
        val view = FragmentListScheduleBinding.inflate(inflater).root

        appComponentsViewModel.havComponent = VisualComponents(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scheduleListAnimation.setAnimation("anim/list_empty.json")
        binding.logoAppAnimation.setAnimation("anim/workdate_app.json")

        val ttb = AnimationUtils.loadAnimation(requireContext(), R.anim.ttb)
        val cardView = binding.scheduleResumeCardView
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
        binding.fragmentListTextMonth.text = Date().formatForTextMonth()
        new_schedule.setOnClickListener {
            goToSearchClientFragment()
        }
        binding.fragmentListRightArrow.setOnClickListener {
            if (month in 0..10)
                month += 1
            updateAdapterWithMonthBusiness()
        }
        binding.fragmentListLeftArrow.setOnClickListener {
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
        binding.fragmentListTextMonth.text = Date(firstOfMonth).formatForTextMonth()
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
            binding.scheduleListAnimation.visibility = GONE
            initAnimation(binding.logoAppAnimation)
            binding.newSchedule.visibility = GONE
            binding.logoAppAnimation.addAnimatorListener(animatorListener(view))
        } else {
            binding.logoAppAnimation.visibility = GONE
        }
    }


    private fun initDonutAnimation() {
        with(binding.fragmentListUiChart) {
            donutColors = ResumeScheduleView.myDonutColors
            animation.duration = ResumeScheduleView.durationDonutAnimation
            animate(ResumeScheduleView.donutSet)
        }
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
                binding.scheduleListAnimation.visibility = GONE
            }

            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animator) {
                initAnimation(binding.scheduleListAnimation)
                binding.newSchedule.visibility = VISIBLE
                binding.logoAppAnimation.visibility = GONE

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
        binding.scheduleListRv.adapter = adapter
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
                        })
                }
            ).showFilterDialog()
        }
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Schedule>) {
        if (mutableList.isEmpty() && loginViewModel.isLogged()) {
            initAnimation(binding.scheduleListAnimation)
        } else {
            binding.scheduleListAnimation.visibility = GONE
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