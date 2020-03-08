package com.br.workdate.view.fragment

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.br.workdate.model.Service

class SearchListServiceFragment : BaseListServiceFragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val arguments by navArgs<SearchListServiceFragmentArgs>()
    private val client by lazy {
        arguments.client
    }
    private val schedule by lazy {
        arguments.schedule
    }

    override fun doInItemClickListener(service: Service) {
        val direction = SearchListServiceFragmentDirections
            .actionSearchListServiceFragmentToFormScheduleFragment(client, service, schedule)
        navController.navigate(direction)
    }
}
