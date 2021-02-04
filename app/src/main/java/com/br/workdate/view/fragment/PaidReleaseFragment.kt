package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.workdate.R
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.view.viewmodel.ReleaseViewModel
import kotlinx.android.synthetic.main.fragment_expense.*
import org.koin.android.viewmodel.ext.android.viewModel

open class PaidReleaseFragment : BaseReleaseFragment() {

    private val viewModel: ReleaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_expense, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        expense_list_animation.setAnimation("anim/list_empty.json")

        viewModel.listAll(Situation.PAID).observe(viewLifecycleOwner, { releases ->
            super.ifEmptyPlayAnimation(releases, expense_list_animation)
            super.initAdapter(releases, expenses_rv)
        })

        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, { releases ->
            if (releases != null) {
                releases.filter { release -> release.situation == Situation.PAID }
                super.ifEmptyPlayAnimation(releases, expense_list_animation)
                super.initAdapter(releases.filter { release ->
                    release.situation == Situation.PAID
                } as MutableList<Release>, expenses_rv)
            }
        })
    }

}