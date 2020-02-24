package com.br.workdate.model

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.br.workdate.extension.formatForBrazilianHour
import java.util.*
import java.util.Calendar.HOUR_OF_DAY
import java.util.Calendar.MINUTE

class TimePickerHelper(
    var onTimeSet: (currentTimeString: String) -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val hour = c.get(HOUR_OF_DAY)
        val minute = c.get(MINUTE)

        return TimePickerDialog(
            context,
            TimePickerDialog.OnTimeSetListener { _, hourOfDay, minuteOfHour ->
                val calendarInstance = Calendar.getInstance()
                calendarInstance.set(HOUR_OF_DAY, hourOfDay)
                calendarInstance.set(MINUTE, minuteOfHour)
                onTimeSet(calendarInstance.time.formatForBrazilianHour())
            },
            hour,
            minute,
            true
        )
    }
}