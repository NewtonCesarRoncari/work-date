package com.br.workdate.model

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*
import java.util.Calendar.*

class DatePickerHelper(
    var onDataSet: (currentDate: Date) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = getInstance()
        val year = c.get(YEAR)
        val month = c.get(MONTH)
        val day = c.get(DAY_OF_MONTH)

        return context?.let { context ->
            DatePickerDialog(
                context,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    val calendarInstance = getInstance()
                    calendarInstance.set(year, month, dayOfMonth, 0, 0, 0)
                    onDataSet(calendarInstance.time)
                },
                year,
                month,
                day
            )
        }!!
    }

}
