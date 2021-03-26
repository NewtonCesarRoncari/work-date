package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.findIconInWindow
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListSchedule : TutorialOf {

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.new_schedule),
                    activity?.getString(R.string.new_schedule),
                    activity?.getString(R.string.new_schedule_message)
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.resume_total),
                    activity?.getString(R.string.show_total),
                    activity?.getString(R.string.show_total_schedule_message),
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconInWindow(width, height),
                    activity?.getString(R.string.tuto_filter),
                    activity?.getString(R.string.tuto_filter_schedule_message)
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}