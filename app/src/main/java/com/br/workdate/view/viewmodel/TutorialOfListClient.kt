package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.findIconInWindow
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListClient : TutorialOf {

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.new_client),
                    activity?.getString(R.string.title_form_new_client),
                    activity?.getString(R.string.new_client_message)
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconInWindow(width, height),
                    activity?.getString(R.string.find_clients),
                    activity?.getString(R.string.find_clients_message)
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
