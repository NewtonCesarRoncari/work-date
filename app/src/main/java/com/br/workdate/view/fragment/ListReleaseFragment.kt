package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.br.workdate.R
import com.br.workdate.view.tabs.adapter.TabsAdapter
import com.br.workdate.view.viewmodel.ReleaseViewModel
import kotlinx.android.synthetic.main.fragment_list_release.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListReleaseFragment : Fragment() {

    private val viewModel: ReleaseViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_release, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initResume()
        initTabLayout(view)
    }

    private fun initResume() {
        lateinit var resume: ResumeView
        val view = activity?.window?.decorView
        viewModel.listAll().observe(viewLifecycleOwner, Observer { releases ->
            resume = view?.let { view -> ResumeView(view, releases) }!!
            resume.update()
        })
    }

    private fun initTabLayout(view: View) {
        val tabsAdapter = TabsAdapter(activity!!.supportFragmentManager)
        val viewPager by lazy {
            view.findViewById<ViewPager>(R.id.fragment_release_viewpager)
        }

        viewPager.adapter = tabsAdapter
        fragment_release_tabLayout.setupWithViewPager(viewPager)
    }
}