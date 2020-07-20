package com.br.workdate.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import com.br.workdate.R
import com.br.workdate.extension.formatForBrazilianDate
import com.br.workdate.model.DatePickerHelper
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog(
    private val context: Context,
    private val activity: FragmentActivity
) {
    private val view = Dialog(context)

    fun showFilterDialog() {
        with(view) {
            setContentView(R.layout.dialog_filter)
            window?.attributes!!.gravity = Gravity.BOTTOM
            window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
            show()
        }

        val items = listOf(
            "Test1",
            "Test2",
            "Test3",
            "Test4",
            "Test5",
            "Test6",
            "Test7",
            "Test8",
            "Test9",
            "Test10",
            "Amanda",
            "Newton",
            "Projeto TCC",
            "Aula de Francês"
        )
        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, items)
        with(view) {
            autoCompleteTextView_client_edit.setAdapter(adapter)
            autoCompleteTextView_service_edit.setAdapter(adapter)

            dialog_filter_from_date_btn.setOnClickListener {
                initDateDialog(view.dialog_filter_from_date_btn)
            }

            dialog_filter_to_date_btn.setOnClickListener {
                initDateDialog(view.dialog_filter_to_date_btn)
            }
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