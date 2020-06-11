package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.lov_screen_two.*

class LovTwoFragment : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lov_screen_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lov_screen_two_animation_one.setAnimation("anim/coracao_preto_branco.json")
        lov_screen_two_animation.setAnimation("anim/tornado.json")
        initAnimation()
        initSecondAnimation()
        lov_screen_two_btn.setOnClickListener {
            goToLovThreeFragment()
        }
    }

    private fun initAnimation() {
        with(lov_screen_two_animation_one) {
            scaleX = 1.5f
            scaleY = 1.5f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun initSecondAnimation() {
        with(lov_screen_two_animation) {
            scaleX = 1.5f
            scaleY = 1.5f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun goToLovThreeFragment() {
        val direction = LovTwoFragmentDirections
            .actionLovTwoFragmentToLovThreeFragment()
        navController.navigate(direction)
    }
}