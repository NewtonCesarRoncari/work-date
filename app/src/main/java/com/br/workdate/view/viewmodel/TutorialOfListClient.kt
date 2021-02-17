package com.br.workdate.view.viewmodel

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListClient : TutorialOf {

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
                    view.findViewById(R.id.new_client),
                    "Novo Cliente",
                    "Aqui você pode iniciar o cadastro de novos clientes, são os clientes para os trabalhos que voce pretende agendar"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconFilterInWindow(width, height),
                    "Encontre seus clientes",
                    "Após cadastrar os clientes, clicando qui é uma ótima forma de encontrá-los"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
