package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.br.workdate.R
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import com.br.workdate.view.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.fragment_form_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FormScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_schedule, container, false)

        appComponentsViewModel.havCoponent = VisualComponents(false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        form_schedule_client_btn.setOnClickListener {
            goToSearchClientFragment()
        }

        form_schedule_service_btn.setOnClickListener {
            goToSearchServiceFragment()
        }
    }

    private fun goToSearchServiceFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListServiceFragment()
        navController.navigate(direction)
    }

    private fun goToSearchClientFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListClientFragment()
        navController.navigate(direction)
    }

}