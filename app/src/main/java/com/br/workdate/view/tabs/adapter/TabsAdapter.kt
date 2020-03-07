package com.br.workdate.view.tabs.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.br.workdate.view.fragment.ExpenseFragment
import com.br.workdate.view.fragment.RevenueFragment

class TabsAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return if (position == 0) {
            RevenueFragment()
        } else {
            ExpenseFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Revenue"
            1 -> return "Expense"
        }
        return null
    }
}