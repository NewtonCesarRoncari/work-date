package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.lov_screen.*

class LovFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lov_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lov_screen_animation.setAnimation("anim/coracao_azul.json")
        initAnimation()
    }

    private fun initAnimation() {
        with(lov_screen_animation) {
            visibility = VISIBLE
            playAnimation()
        }
    }
}