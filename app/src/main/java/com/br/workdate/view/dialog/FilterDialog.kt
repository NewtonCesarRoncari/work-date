package com.br.workdate.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.model.DatePickerHelper
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog(
    context: Context,
    private val activity: FragmentActivity,
    private val tag: String,
    var loadClientNames: (clientAutoComplete: AutoCompleteTextView) -> Unit = {},
    var loadServiceDescriptions: (serviceAutoComplete: AutoCompleteTextView) -> Unit = {}
) {
    private val view = Dialog(context)

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
            initDateDialog(view.dialog_filter_from_date_btn)
        }

        view.dialog_filter_to_date_btn.setOnClickListener {
            initDateDialog(view.dialog_filter_to_date_btn)
        }
    }

    private fun initDateDialog(button: Button) {
        val datePicker = DatePickerHelper(
            onDataSet = { currentDate ->
                button.text = currentDate.formatForBrazilianDate()
            }
        )
        activity.supportFragmentManager.let { fragmentManager ->
            datePicker.show(fragmentManager, "time picker")
        }
    }
}