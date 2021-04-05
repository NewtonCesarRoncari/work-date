package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.workdate.databinding.FragmentExpenseBinding
import com.br.workdate.model.Situation
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding

open class PaidReleaseFragment : BaseReleaseFragment() {

    private val binding by viewBinding(FragmentExpenseBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentExpenseBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.expenseListAnimation.setAnimation("anim/list_empty.json")

        super.listAll(Situation.PAID, binding.expenseListAnimation, binding.expensesRv)
        super.checkReleasesReturned(
            Situation.PAID,
            binding.expenseListAnimation,
            binding.expensesRv
        )
    }

}