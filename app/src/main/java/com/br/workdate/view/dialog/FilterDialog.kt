package com.br.workdate.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.widget.ArrayAdapter
import com.br.workdate.R
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog(
    private val context: Context
) {
    private val view = Dialog(context)

    fun showFilterDialog() {
        view.setContentView(R.layout.dialog_filter)
        view.window?.attributes!!.gravity = Gravity.BOTTOM
        view.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        view.show()

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
            "Test10"
        )
        val adapter = ArrayAdapter(context, R.layout.support_simple_spinner_dropdown_item, items)
        view.autoCompleteTextView_client_edit.setAdapter(adapter)
        view.autoCompleteTextView_service_edit.setAdapter(adapter)
    }
}