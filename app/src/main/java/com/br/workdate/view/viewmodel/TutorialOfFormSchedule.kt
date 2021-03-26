package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfFormSchedule : TutorialOf {

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_client_name),
                    activity?.getString(R.string.chosen_client),
                    activity?.getString(R.string.chosen_client_message)
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_service_description),
                    activity?.getString(R.string.chosen_service),
                    activity?.getString(R.string.chosen_service_message)
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_date_btn),
                    activity?.getString(R.string.choosing_date),
                    activity?.getString(R.string.choosing_date_message)
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_hour_btn),
                    activity?.getString(R.string.choosing_time),
                    activity?.getString(R.string.choosing_time_message)
                ).transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
