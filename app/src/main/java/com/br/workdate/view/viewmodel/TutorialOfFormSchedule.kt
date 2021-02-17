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
                    "Cliente selecionado",
                    "Este é o seu cliente selecionado, sempre aparecerá aqui para verificação"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_service_description),
                    "Serviço selecionado",
                    "Este é o seu serviço selecionado, sempre aparecerá aqui para verificação"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_date_btn),
                    "Informando uma data",
                    "Aqui você pode informar a data que pretende realizar o trabalho"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.form_schedule_hour_btn),
                    "Informando a hora",
                    "Aqui você pode informar a hora que pretende realizar o trabalho"
                ).transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
