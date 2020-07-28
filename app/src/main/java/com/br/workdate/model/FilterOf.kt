package com.br.workdate.model

import android.app.Dialog

interface FilterOf {

    val tag: String
    fun alterComponentsByTag(view: Dialog)
    fun chipOneSetOnClickListener(view: Dialog): String
    fun chipTwoSetOnClickListener(view: Dialog): String
    fun loadAndReturnParams(
        view: Dialog,
        fromDate: String,
        toDate: String,
        chipOne: String,
        chipTwo: String,
        releaseChip: String
    ): HashMap<String, String>
}