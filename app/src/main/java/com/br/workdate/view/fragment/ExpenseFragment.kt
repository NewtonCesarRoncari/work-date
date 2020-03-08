package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.br.workdate.R
import kotlinx.android.synthetic.main.fragment_expense.*

class ExpenseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expense_list_animation.setAnimation("anim/list_empty.json")

        initAnimation()
    }

    private fun initAnimation() {
        with(expense_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = View.VISIBLE
            playAnimation()
        }
    }
}