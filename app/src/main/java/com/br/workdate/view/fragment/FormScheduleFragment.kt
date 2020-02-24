package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianCoin
import com.br.workdate.extension.limit
import com.br.workdate.model.Client
import com.br.workdate.model.Service
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import com.br.workdate.view.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.fragment_form_schedule.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FormScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val arguments by navArgs<FormScheduleFragmentArgs>()
    private val client by lazy {
        arguments.client
    }
    private val service by lazy {
        arguments.service
    }

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

        client?.let { tryLoadClientFields(it) }
        service?.let { tryLoadServiceFields(it) }

        form_schedule_client_btn.setOnClickListener {
            goToSearchClientFragment()
        }

        form_schedule_service_btn.setOnClickListener {
            goToSearchServiceFragment()
        }

//        form_schedule_date_btn.setOnClickListener {
//            val datePicker = DatePickerHelper()
//            datePicker.show(getSupportFragmentManager(), "date picker")
//        }
    }

    private fun tryLoadClientFields(client: Client) {
        form_schedule_client_btn.text = client.name.limit(maxCharacters = 24)
        form_schedule_client_phone.text = client.phone
    }

    private fun tryLoadServiceFields(service: Service) {
        form_schedule_service_btn.text = service.description.limit(maxCharacters = 21)
        form_schedule_service_value.text = service.value.formatForBrazilianCoin()
    }

    private fun goToSearchClientFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListClientFragment(service)
        navController.navigate(direction)
    }

    private fun goToSearchServiceFragment() {
        val direction = FormScheduleFragmentDirections
            .actionFormScheduleFragmentToSearchListServiceFragment(client)
        navController.navigate(direction)
    }

}