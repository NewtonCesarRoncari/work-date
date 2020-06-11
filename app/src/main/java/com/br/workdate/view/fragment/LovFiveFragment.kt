package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.lov_screen_five.*

class LovFiveFragment : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.lov_screen_five, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        lov_screen_five_btn.setOnClickListener {
            goToLovSixFragment()
        }
    }

    private fun goToLovSixFragment() {
        val direction = LovFiveFragmentDirections
            .actionLovFiveFragmentToLovSixFragment()
        navController.navigate(direction)
    }
}