package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.br.workdate.databinding.FragmentRevenueBinding
import com.br.workdate.model.Release
import com.br.workdate.model.Situation
import com.br.workdate.view.viewmodel.ReleaseViewModel
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class OpenReleaseFragment : BaseReleaseFragment() {

    private val binding by viewBinding(FragmentRevenueBinding::bind)
    private val viewModel: ReleaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentRevenueBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.revenueListAnimation.setAnimation("anim/list_empty.json")

        viewModel.listAll(Situation.OPEN).observe(viewLifecycleOwner, { releases ->
            super.ifEmptyPlayAnimation(releases, binding.revenueListAnimation)
            super.initAdapter(releases, binding.revenueRv)
        })

        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, { releases ->
            if (releases != null) {
                super.ifEmptyPlayAnimation(releases, binding.revenueListAnimation)
                super.initAdapter(releases.filter { release ->
                    release.situation == Situation.OPEN
                } as MutableList<Release>, binding.revenueRv)
            }
        })
    }
}
