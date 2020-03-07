package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.br.workdate.R
import com.br.workdate.view.tabs.adapter.TabsAdapter
import kotlinx.android.synthetic.main.fragment_list_release.*

class ListReleaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_release, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTabLayout(view)
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