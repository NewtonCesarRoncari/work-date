package com.br.workdate.view.viewmodel

import android.app.Dialog
import com.br.workdate.R
import com.br.workdate.model.*
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterOfRelease : FilterOf {

    override val tag get() = "RELEASE"

    override fun alterComponentsByTag(view: Dialog) {
        view.chip_one.setText(R.string.open)
        view.chip_two.setText(R.string.paid)
    }

    override fun chipOneSetOnClickListener(view: Dialog): String {
        view.chip_two.isChecked = false
        return "0"
    }

    override fun chipTwoSetOnClickListener(view: Dialog): String {
        view.chip_one.isChecked = false
        return "1"
    }

    override fun loadAndReturnParams(
        view: Dialog,
        fromDate: String,
        toDate: String,
        chipOne: String,
        chipTwo: String,
        releaseChip: String
    ): HashMap<String, String> {
        val params = hashMapOf<String, String>()
        if (view.autoCompleteTextView_client_edit.text.toString().trim().isNotEmpty()) {
            params[@Params CLIENT_NAME] =
                view.autoCompleteTextView_client_edit.text.toString().trim()
        }
        if (view.autoCompleteTextView_service_edit.text.toString().trim().isNotEmpty()) {
            params[@Params SERVICE_DESCRIPTION] =
                view.autoCompleteTextView_service_edit
                    .text.toString().trim()
        }
        if (fromDate.trim() != "0") {
            params[@Params FROM_DATE] = fromDate.trim()
        }
        if (toDate.trim() != "0") {
            params[@Params TO_DATE] = toDate.trim()
        }
        if (view.chip_one.isChecked || view.chip_two.isChecked) {
            if (releaseChip.trim().isNotEmpty()) {
                params[@Params SITUATION] = releaseChip
            }
        }
        return params
    }
}