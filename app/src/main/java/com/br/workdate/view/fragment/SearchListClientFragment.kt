package com.br.workdate.view.fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.br.workdate.model.Client

class SearchListClientFragment : BaseListClientFragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val arguments by navArgs<SearchListClientFragmentArgs>()
    private val service by lazy {
        arguments.service
    }

    override fun doInItemClickListener(client: Client) {
        val direction = SearchListClientFragmentDirections
            .actionSearchListClientFragmentToFormScheduleFragment(client, service, null)
        navController.navigate(direction)
    }
}