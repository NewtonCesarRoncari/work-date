package com.br.workdate.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.extension.impalementSingleQuotes
import com.br.workdate.model.*
import kotlinx.android.synthetic.main.dialog_filter.*
import java.util.*
import java.util.Calendar.getInstance

class FilterDialog(
    context: Context,
    private val activity: FragmentActivity,
    private val tag: String,
    var loadClientNames: (clientAutoComplete: AutoCompleteTextView) -> Unit = {},
    var loadServiceDescriptions: (serviceAutoComplete: AutoCompleteTextView) -> Unit = {},
    var returnQuery: (query: String) -> Unit = {}
) {
    private val view = Dialog(context)
    private var fromDate: Long = 0
    private var toDate: Long = 0
    private var releaseChip = ""
    private var scheduleChipOne = ""
    private var scheduleChipTwo = ""

    private fun alterComponentsByTag(tag: String) {
        if (tag == "SCHEDULE") {
            view.chip_one.setText(R.string.canceled)
            view.chip_two.setText(R.string.finished)
        }
        if (tag == "RELEASE") {
            view.chip_one.setText(R.string.open)
            view.chip_two.setText(R.string.paid)
        }
    }

    fun showFilterDialog() {
        with(view) {
            setContentView(R.layout.dialog_filter)
            window?.attributes!!.gravity = Gravity.BOTTOM
            window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            alterComponentsByTag(tag)
            show()
        }

        loadClientNames(view.autoCompleteTextView_client_edit)
        loadServiceDescriptions(view.autoCompleteTextView_service_edit)

        view.chip_one.setOnClickListener {
            if (tag == "RELEASE") {
                view.chip_two.isChecked = false
                this.releaseChip = "0"
            }
            if (tag == "SCHEDULE") {
                if (view.chip_one.isChecked) {
                    this.scheduleChipOne = "1"
                } else {
                    this.scheduleChipOne = "0"
                }
            }
        }

        view.chip_two.setOnClickListener {
            if (tag == "RELEASE") {
                view.chip_one.isChecked = false
                this.releaseChip = "1"
            }
            if (tag == "SCHEDULE") {
                if (view.chip_two.isChecked) {
                    this.scheduleChipTwo = "1"
                } else {
                    this.scheduleChipTwo = "0"
                }
            }
        }

        view.dialog_filter_from_date_btn.setOnClickListener {
            initDateDialogFromDate(view.dialog_filter_from_date_btn)
        }

        view.dialog_filter_to_date_btn.setOnClickListener {
            initDateDialogToDate(view.dialog_filter_to_date_btn)
        }

        view.dialog_filter_save_btn.setOnClickListener {
            val queryCreatorFilter = QueryCreatorFilter()
            val query = queryCreatorFilter.returnByParams(loadAndReturnParams(), tag)
            clearVariables()
            returnQuery(query)
            view.dismiss()
            Log.i("query", query)
        }
    }

    private fun clearVariables() {
        fromDate = 0
        toDate = 0
        releaseChip = ""
        scheduleChipOne = ""
        scheduleChipTwo = ""
    }

    private fun initDateDialogFromDate(button: Button) {
        val datePicker = DatePickerHelper(
            onDataSet = { currentDate ->
                Log.i("date", currentDate.toString())
                Log.i("date", currentDate.time.toString())
                fromDate = currentDate.time
                button.text = currentDate.formatForBrazilianDate()
            }
        )
        activity.supportFragmentManager.let { fragmentManager ->
            datePicker.show(fragmentManager, "time picker")
        }
    }

    private fun initDateDialogToDate(button: Button) {
        val datePicker = DatePickerHelper(
            onDataSet = { currentDate ->
                toDate = addOneDay(currentDate)
                button.text = currentDate.formatForBrazilianDate()
            }
        )
        activity.supportFragmentManager.let { fragmentManager ->
            datePicker.show(fragmentManager, "time picker")
        }
    }

    private fun addOneDay(currentDate: Date): Long {
        val cal = getInstance()
        cal.time = currentDate
        cal.add(Calendar.DATE, 1)
        return cal.time.time
    }

    private fun loadAndReturnParams(): HashMap<String, String> {
        val params = hashMapOf<String, String>()
        if (view.autoCompleteTextView_client_edit.text.toString().trim().isNotEmpty()) {
            params[@Params CLIENT_NAME] =
                view.autoCompleteTextView_client_edit.text.toString().trim()
                    .impalementSingleQuotes()
        }
        if (view.autoCompleteTextView_service_edit.text.toString().trim().isNotEmpty()) {
            params[@Params SERVICE_DESCRIPTION] =
                view.autoCompleteTextView_service_edit.text.toString().trim()
                    .impalementSingleQuotes()
        }
        if (fromDate.toString().trim() != "0") {
            params[@Params FROM_DATE] = fromDate.toString().trim().impalementSingleQuotes()
        }
        if (toDate.toString().trim() != "0") {
            params[@Params TO_DATE] = toDate.toString().trim().impalementSingleQuotes()
        }
        if (tag == "RELEASE") {
            if (view.chip_one.isChecked && view.chip_two.isChecked) {
                if (releaseChip.trim().isNotEmpty()) {
                    params[@Params SITUATION] = releaseChip
                }
            }
        }
        if (tag == "SCHEDULE") {
            if (scheduleChipOne != "0" && scheduleChipOne != "0") {
                if (scheduleChipOne.trim().isNotEmpty()) {
                    params[@Params CANCELED] = scheduleChipOne
                }
                if (scheduleChipTwo.trim().isNotEmpty()) {
                    params[@Params FINISHED] = scheduleChipTwo
                }
            }
        }
        return params
    }
}