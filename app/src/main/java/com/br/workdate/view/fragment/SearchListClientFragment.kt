package com.br.workdate.view.fragment

import com.br.workdate.model.Client
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchListClientFragment : BaseListClientFragment() {

    override fun implementIfWannaHideFab(fab: FloatingActionButton) {
        fab.hide()
    }

    override fun doInFabClickListener() {}

    override fun doInItemClickListener(client: Client) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doInItemLongClickListener(client: Client) {}
}