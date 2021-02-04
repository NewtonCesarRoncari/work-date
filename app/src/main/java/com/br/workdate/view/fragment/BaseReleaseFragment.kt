package com.br.workdate.view.fragment

import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.br.workdate.model.Release
import com.br.workdate.view.recyclerview.adapter.ReleaseAdapter

open class BaseReleaseFragment : Fragment() {

    protected fun initAdapter(releases: MutableList<Release>, recyclerView: RecyclerView) {
        val adapter = context?.let { ReleaseAdapter(it, releases) }
        recyclerView.adapter = adapter
    }

    protected fun ifEmptyPlayAnimation(
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
}