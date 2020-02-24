package com.br.workdate.model

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.Calendar.*

class DatePickerHelper : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = getInstance()
        val year = c.get(YEAR)
        val month = c.get(MONTH)
        val day = c.get(DAY_OF_MONTH)

        return context?.let {
            DatePickerDialog(
                it,
                activity as OnDateSetListener?,
                year,
                month,
                day
            )
        }!!
    }
}
