package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.lov_screen_one.*

class LovOneFragment : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lov_screen_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lov_screen_one_animation.setAnimation("anim/boy.json")
        initAnimation()
        lov_screen_one_btn.setOnClickListener {
            goToLovTwoFragment()
        }
    }

    private fun initAnimation() {
        with(lov_screen_one_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = View.VISIBLE
            playAnimation()
        }
    }

    private fun goToLovTwoFragment() {
        val direction = LovOneFragmentDirections
            .actionLovOneFragmentToLovTwoFragment()
        navController.navigate(direction)
    }
}