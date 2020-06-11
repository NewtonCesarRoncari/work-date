package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.lov_screen_three.*

class LovThreeFragment : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lov_screen_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lov_screen_three_animation_one.setAnimation("anim/pizza.json")
        lov_screen_three_animation.setAnimation("anim/chocolate.json")
        initAnimation()
        initSecondAnimation()
        lov_screen_three_btn.setOnClickListener {
            goToLovFourFragment()
        }
    }

    private fun initAnimation() {
        with(lov_screen_three_animation_one) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun initSecondAnimation() {
        with(lov_screen_three_animation) {
            scaleX = 0.4f
            scaleY = 0.4f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun goToLovFourFragment() {
        val direction = LovThreeFragmentDirections
            .actionLovThreeFragmentToLovFourFragment()
        navController.navigate(direction)
    }
}