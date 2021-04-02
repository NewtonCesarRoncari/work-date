package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.workdate.databinding.FragmentExpenseBinding
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.view.viewmodel.ReleaseViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

open class PaidReleaseFragment : BaseReleaseFragment() {

    private val binding by viewBinding(FragmentExpenseBinding::bind)
    private val viewModel: ReleaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentExpenseBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.expenseListAnimation.setAnimation("anim/list_empty.json")

        viewModel.listAll(Situation.PAID).observe(viewLifecycleOwner, { releases ->
            super.ifEmptyPlayAnimation(releases, binding.expenseListAnimation)
            super.initAdapter(releases, binding.expensesRv)
        })

        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, { releases ->
            if (releases != null) {
                releases.filter { release -> release.situation == Situation.PAID }
                super.ifEmptyPlayAnimation(releases, binding.expenseListAnimation)
                super.initAdapter(releases.filter { release ->
                    release.situation == Situation.PAID
                } as MutableList<Release>, binding.expensesRv)
            }
        })
    }

}