package com.br.workdate.view.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.view.recyclerview.adapter.ReleaseAdapter
import com.br.workdate.view.viewmodel.ReleaseViewModel
import org.koin.android.viewmodel.ext.android.viewModel

open class BaseReleaseFragment : Fragment() {

    private val viewModel: ReleaseViewModel by viewModel()

    private fun initAdapter(releases: MutableList<Release>, recyclerView: RecyclerView) {
        val adapter = context?.let { ReleaseAdapter(it, releases) }
        recyclerView.adapter = adapter
    }

    private fun ifEmptyPlayAnimation(
        mutableList: MutableList<Release>,
        listAnimation: LottieAnimationView
    ) {
        if (mutableList.isEmpty()) {
            initAnimation(listAnimation)
        } else {
            listAnimation.visibility = View.GONE
        }
    }

    private fun initAnimation(listAnimation: LottieAnimationView) {
        with(listAnimation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = View.VISIBLE
            playAnimation()
        }
    }

    protected fun listAll(
        situation: Situation,
        listAnimation: LottieAnimationView,
        recyclerView: RecyclerView
    ) {
        viewModel.listAll(situation).observe(viewLifecycleOwner, { releases ->
            ifEmptyPlayAnimation(releases, listAnimation)
            initAdapter(releases, recyclerView)
        })
    }

    protected fun checkReleasesReturned(
        situation: Situation,
        listAnimation: LottieAnimationView,
        recyclerView: RecyclerView
    ) {
        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, { releases ->
            if (releases != null) {
                ifEmptyPlayAnimation(releases, listAnimation)
                initAdapter(releases.filter { release ->
                    release.situation == situation
                } as MutableList<Release>, recyclerView)
            }
        })
    }
}