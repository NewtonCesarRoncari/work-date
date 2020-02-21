package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.br.workdate.R
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import com.br.workdate.view.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel

class FormScheduleFragment : Fragment() {

    private val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_form_schedule, container, false)

        appComponentsViewModel.havCoponent = VisualComponents(false)
        return view
    }

}