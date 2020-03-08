package com.br.workdate.view.fragment

import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.model.Client
import com.br.workdate.view.viewmodel.VisualComponents

class SearchListClientFragment : BaseListClientFragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun doInOnCreateView() {
        super.appComponentsViewModel.havCoponent = VisualComponents(false)
    }

    override fun doInItemClickListener(client: Client) {
        val direction = SearchListClientFragmentDirections
            .actionSearchListClientFragmentToSearchListServiceFragment(client = client)
        navController.navigate(direction)
    }
}