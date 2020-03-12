package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.br.workdate.R
import com.br.workdate.model.Release
import com.br.workdate.model.Type
import com.br.workdate.view.recyclerview.adapter.ReleaseAdapter
import com.br.workdate.view.viewmodel.ReleaseViewModel
import kotlinx.android.synthetic.main.fragment_revenue.*
import org.koin.android.viewmodel.ext.android.viewModel

class RevenueFragment : Fragment() {

    private val viewModel: ReleaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_revenue, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        revenue_list_animation.setAnimation("anim/list_empty.json")

        viewModel.listAll(Type.REVENUE).observe(viewLifecycleOwner, Observer { releases ->
            ifEmptyPlayAnimation(releases)
            initAdapter(releases)
        })
    }

    private fun initAdapter(releases: MutableList<Release>) {
        val adapter = context?.let { ReleaseAdapter(it, releases) }
        revenue_rv.adapter = adapter
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Release>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            revenue_list_animation.visibility = View.GONE
        }
    }

    private fun initAnimation() {
        with(revenue_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }
}
