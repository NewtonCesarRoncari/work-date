package com.br.workdate.view.viewmodel

import android.graphics.Rect
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListService : TutorialOf {

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
                    view.findViewById(R.id.new_service),
                    "Novo Serviço",
                    "Aqui você pode iniciar o cadastro de novos serviços ou trabalhos os trabalhos que você pretende agendar para algum cliente"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconFilterInWindow(width, height),
                    "Encontre seus Serviços",
                    "Após cadastrar os serviços, clicando qui é uma ótima forma de encontrá-los"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
