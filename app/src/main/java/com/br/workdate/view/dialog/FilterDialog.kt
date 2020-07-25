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
import com.br.workdate.extension.booleanDatabase
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.extension.impalementSingleQuotes
import com.br.workdate.model.*
import kotlinx.android.synthetic.main.dialog_filter.*

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

    private fun alterComponentsByTag(tag: String) {
        if (tag == "schedule") {
            view.chip_one.setText(R.string.canceled)
            view.chip_two.setText(R.string.finished)
        } else {
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

        view.dialog_filter_from_date_btn.setOnClickListener {
            initDateDialogFromDate(view.dialog_filter_from_date_btn)
        }

        view.dialog_filter_to_date_btn.setOnClickListener {
            initDateDialogToDate(view.dialog_filter_to_date_btn)
        }

        view.dialog_filter_save_btn.setOnClickListener {
            val queryCreatorFilter = QueryCreatorFilter()
            val query = queryCreatorFilter.returnByParams(loadAndReturnParams())
            fromDate = 0
            toDate = 0
            returnQuery(query)
            view.dismiss()
            Log.i("query", query)
        }
    }

    private fun initDateDialogFromDate(button: Button) {
        val datePicker = DatePickerHelper(
            onDataSet = { currentDate ->
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
                toDate = currentDate.time
                button.text = currentDate.formatForBrazilianDate()
            }
        )
        activity.supportFragmentManager.let { fragmentManager ->
            datePicker.show(fragmentManager, "time picker")
        }
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
        params[@Params SITUATION] =
            view.chip_one.isChecked.toString().booleanDatabase().trim().impalementSingleQuotes()
        return params
    }
}