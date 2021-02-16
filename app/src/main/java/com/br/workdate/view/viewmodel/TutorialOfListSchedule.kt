package com.br.workdate.view.viewmodel

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListSchedule: TutorialOf {

    private fun findIconFilterInWindow(width: Int, height: Int): Rect {
        val x = width * 2
        val horizontalDistance = x - (x * 0.0625)
        val verticalDistance = height * 0.15325

        return Rect(
            0,
            0,
            horizontalDistance.toInt(),
            verticalDistance.toInt()
        )
    }

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.new_schedule),
                    "Novo Agendamento",
                    "Aqui voce pode iniciar o cadastro de novos agendamentos, para agendar o trabalho que pretende realizar"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forView(
                    view.findViewById(R.id.resume_total),
                    "Aqui voce pode ver o total",
                    "É mostrado a soma dos agendamentos concluídos e não concluídos"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconFilterInWindow(width, height),
                    "Filtro",
                    "Após agendar os trabalhos que pretende realizar, aqui voce pode filtrá-los, uma ótima forma de encontrar seus trabalhos agendados"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}