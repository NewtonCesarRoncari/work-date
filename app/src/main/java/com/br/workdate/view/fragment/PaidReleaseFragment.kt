package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.br.workdate.R
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.view.recyclerview.adapter.ReleaseAdapter
import com.br.workdate.view.viewmodel.ReleaseViewModel
import kotlinx.android.synthetic.main.fragment_expense.*
import org.koin.android.viewmodel.ext.android.viewModel

class PaidReleaseFragment : Fragment() {

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

        viewModel.listAll(Situation.PAID).observe(viewLifecycleOwner, Observer { releases ->
            ifEmptyPlayAnimation(releases)
            initAdapter(releases)
        })

        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, Observer { releases ->
            if (releases != null) {
                releases.filter { release -> release.situation == Situation.PAID }
                ifEmptyPlayAnimation(releases)
                initAdapter(releases.filter { release ->
                    release.situation == Situation.PAID
                } as MutableList<Release>)
            }
        })
    }

    private fun initAdapter(releases: MutableList<Release>) {
        val adapter = context?.let { ReleaseAdapter(it, releases) }
        expenses_rv.adapter = adapter
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Release>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            expense_list_animation.visibility = View.GONE
        }
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