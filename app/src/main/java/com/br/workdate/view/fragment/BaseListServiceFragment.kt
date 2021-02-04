package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.br.workdate.R
import com.br.workdate.model.Service
import com.br.workdate.view.dialog.BaseDialog
import com.br.workdate.view.dialog.ServiceFormInsertDialog
import com.br.workdate.view.recyclerview.adapter.ServiceAdapter
import com.br.workdate.view.viewmodel.ServiceViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_service.*
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseListServiceFragment : Fragment() {

    private lateinit var adapter: ServiceAdapter
    protected val viewModel: ServiceViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_service, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        service_list_animation.setAnimation("anim/list_empty.json")

        new_service.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, { serviceList ->
            ifEmptyPlayAnimation(serviceList)
            initServiceAdapter(serviceList)
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.base_search_menu, menu)

        val searchItem by lazy { menu.findItem(R.id.action_search) }
        val searchView by lazy { searchItem.actionView as SearchView }

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (::adapter.isInitialized) {
                    adapter.filter.filter(newText)
                }
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initServiceAdapter(services: MutableList<Service>) {
        adapter = context?.let { context -> ServiceAdapter(context, services) }!!
        service_list_rv.adapter = adapter
        adapter.onItemClickListener = { service -> doInItemClickListener(service) }
        adapter.onItemLongClickListener = { service -> removeInDataBase(service) }
    }

    private fun removeInDataBase(service: Service) {
        viewModel.remove(
            service,
            inFailureCase = { inFailureCase(getString(R.string.message_linked_schedule)) },
            inSuccessCase = { inSuccessCase(service, getString(R.string.removed)) }
        )
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Service>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            service_list_animation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(service_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ServiceFormInsertDialog(view as ViewGroup, context)
                .initServiceFormDialog { serviceReturned ->
                    insertInDB(serviceReturned)
                }
        }
    }

    private fun insertInDB(serviceReturned: Service) {
        viewModel.insert(
            serviceReturned,
            inFailureCase = { inFailureCase(getString(R.string.message_service_description_already_exists)) },
            inSuccessCase = { inSuccessCase(serviceReturned, getString(R.string.saved)) }
        )
    }

    private fun inSuccessCase(serviceReturned: Service, message: String) {
        activity?.runOnUiThread {
            showSnackBar(serviceReturned, message)
        }
    }

    private fun inFailureCase(messageError: String) {
        activity?.runOnUiThread {
            val baseDialog = BaseDialog(requireContext())
            baseDialog.showErrorRemoveDialog(messageError)
        }
    }

    protected fun showSnackBar(service: Service, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${service.description} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    abstract fun doInItemClickListener(service: Service)
}
