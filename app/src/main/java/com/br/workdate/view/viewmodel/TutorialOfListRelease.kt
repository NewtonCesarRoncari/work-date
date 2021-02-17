package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.findIconInWindow
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListRelease : TutorialOf {

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.resume_total),
                    "Aqui você pode ver o total",
                    "É mostrado a soma dos lançamentos financeiros gerados pelos agendamentos, nessa mesma tela é possível verificar os lançamentos em aberto e também os pagos"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconInWindow(width, height),
                    "Filtro",
                    "Após a geração dos lançamentos financeiros criados a partir dos agendamentos, você pode filtrá-los, uma ótima forma de encontrar seus lançamentos financeiros e saber o resumo dos valores"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}