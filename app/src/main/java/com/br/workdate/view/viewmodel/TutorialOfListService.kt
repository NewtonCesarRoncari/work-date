package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.findIconInWindow
import com.getkeepsafe.taptargetview.TapTarget
import com.getkeepsafe.taptargetview.TapTargetSequence

class TutorialOfListService : TutorialOf {

    override fun initTutorial(activity: FragmentActivity?, view: View, width: Int, height: Int) {
        val sequence = TapTargetSequence(activity)
            .targets(
                TapTarget.forView(
                    view.findViewById(R.id.new_service),
                    "Novo Serviço",
                    "Aqui você pode iniciar o cadastro de novos serviços ou trabalhos os trabalhos que você pretende agendar para algum cliente"
                ).transparentTarget(true).cancelable(false),
                TapTarget.forBounds(
                    findIconInWindow(width, height),
                    "Encontre seus Serviços",
                    "Após cadastrar os serviços, clicando aqui é uma ótima forma de encontrá-los"
                )
                    .transparentTarget(true).cancelable(false)
            )
        sequence.start()
    }
}
