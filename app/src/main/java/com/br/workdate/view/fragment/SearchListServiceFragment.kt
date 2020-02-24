package com.br.workdate.view.fragment

import com.br.workdate.model.Service
import com.google.android.material.floatingactionbutton.FloatingActionButton

class SearchListServiceFragment : BaseListServiceFragment() {

    override fun implementIfWannaHideFab(newService: FloatingActionButton) {
        newService.hide()
    }

    override fun doInFabClickListener() {}

    override fun doInItemClickListener(service: Service) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun doInItemLongClickListener(service: Service) {}
}
