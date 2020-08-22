package com.br.workdate.view.tabs.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.br.workdate.R
import com.br.workdate.view.fragment.OpenReleaseFragment
import com.br.workdate.view.fragment.PaidReleaseFragment

class TabsAdapter(fm: FragmentManager, val context: Context) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            OpenReleaseFragment()
        } else {
            PaidReleaseFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return context.getString(R.string.open)
            1 -> return context.getString(R.string.paid)
        }
        return null
    }
}