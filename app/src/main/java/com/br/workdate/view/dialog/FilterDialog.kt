package com.br.workdate.view.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color.TRANSPARENT
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import com.br.workdate.R

class FilterDialog(
    private val context: Context
) {
    private val view = initView()

    fun showFilterDialog() {
        view.setContentView(R.layout.dialog_filter)
        view.window?.attributes!!.gravity = Gravity.BOTTOM
        view.window?.setBackgroundDrawable(ColorDrawable(TRANSPARENT))
        view.show()
    }

    private fun initView(): Dialog {
        return Dialog(context)
    }
}