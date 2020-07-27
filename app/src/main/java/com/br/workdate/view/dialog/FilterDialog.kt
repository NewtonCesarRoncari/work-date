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
import com.br.workdate.model.DatePickerHelper
import com.br.workdate.model.FilterOf
import com.br.workdate.model.QueryCreatorFilter
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog(
    context: Context,
    private val activity: FragmentActivity,
    private val filterOf: FilterOf,
    var loadClientNames: (clientAutoComplete: AutoCompleteTextView) -> Unit = {},
    var loadServiceDescriptions: (serviceAutoComplete: AutoCompleteTextView) -> Unit = {},
    var returnQuery: (query: String) -> Unit = {}
) {
    private val tag = filterOf.tag()
    private val view = Dialog(context)
    private var fromDate: Long = 0
    private var toDate: Long = 0
    private var releaseChip = ""
    private var chipOne = ""
    private var chipTwo = ""

    private fun alterComponentsByTag() {
        filterOf.alterComponentsByTag(view)
    }

    fun showFilterDialog() {
        with(view) {
            setContentView(R.layout.dialog_filter)
            window?.attributes!!.gravity = Gravity.BOTTOM
            window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            alterComponentsByTag()
            show()

            loadClientNames(view.autoCompleteTextView_client_edit)
            loadServiceDescriptions(view.autoCompleteTextView_service_edit)

            chip_one.setOnClickListener {
                releaseChip = filterOf.chipOneSetOnClickListener(view)
                chipOne = filterOf.chipOneSetOnClickListener(view)
            }

            chip_two.setOnClickListener {
                releaseChip = filterOf.chipTwoSetOnClickListener(view)
                chipTwo = filterOf.chipTwoSetOnClickListener(view)
            }

            dialog_filter_from_date_btn.setOnClickListener {
                initDateDialogDate(view.dialog_filter_from_date_btn, "from")
            }

            dialog_filter_to_date_btn.setOnClickListener {
                initDateDialogDate(view.dialog_filter_to_date_btn, "to")
            }

            dialog_filter_save_btn.setOnClickListener {
                val queryCreatorFilter = QueryCreatorFilter()
                val params = returnParams()
                val query = queryCreatorFilter.returnByParams(params, tag)
                clearVariables()
                returnQuery(query)
                view.dismiss()
                Log.i("query", query)
            }
        }
    }

    private fun returnParams(): HashMap<String, String> {
        return filterOf.loadAndReturnParams(
            view,
            fromDate.toString(),
            toDate.toString(),
            chipOne,
            chipTwo,
            releaseChip
        )
    }

    private fun clearVariables() {
        fromDate = 0
        toDate = 0
        releaseChip = ""
        chipOne = ""
        chipTwo = ""
    }

    private fun initDateDialogDate(button: Button, fromOrTo: String) {
        val datePicker = DatePickerHelper(
            onDataSet = { currentDate ->
                if (fromOrTo == "from") fromDate = currentDate.time
                if (fromOrTo == "to") toDate = currentDate.time
                fromDate = currentDate.time
                button.text = currentDate.formatForBrazilianDate()
            }
        )
        activity.supportFragmentManager.let { fragmentManager ->
            datePicker.show(fragmentManager, "time picker")
        }
    }
}