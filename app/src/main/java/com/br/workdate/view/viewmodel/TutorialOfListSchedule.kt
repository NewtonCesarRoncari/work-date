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
                    "Novo Agendamento",
                    "Aqui você pode iniciar o cadastro de novos agendamentos, para agendar o trabalho que pretende realizar"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.resume_total),
                    "Aqui você pode ver o total",
                    "É mostrado a soma dos agendamentos concluídos e não concluídos"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconInWindow(width, height),
                    "Filtro",
                    "Após agendar os trabalhos que pretende realizar, aqui você pode filtrá-los, uma ótima forma de encontrar seus trabalhos agendados"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}