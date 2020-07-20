package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.br.workdate.R
import com.br.workdate.model.Client
import com.br.workdate.view.dialog.BaseDialog
import com.br.workdate.view.dialog.ClientFormInsertDialog
import com.br.workdate.view.recyclerview.adapter.ClientAdapter
import com.br.workdate.view.viewmodel.ClientViewModel
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list_client.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseListClientFragment : Fragment() {

    protected val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private lateinit var adapter: ClientAdapter
    protected val viewModel: ClientViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_client, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        client_list_animation.setAnimation("anim/list_empty.json")
        doInOnCreateView()

        new_client.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, Observer { clientList ->
            ifEmptyPlayAnimation(clientList)
            initClientAdapter(clientList)
        })
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.base_search_menu, menu)

        val searchItem by lazy { menu.findItem(R.id.action_search) }
        val searchView by lazy { searchItem.actionView as SearchView }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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

    private fun initClientAdapter(clients: MutableList<Client>) {
        adapter = context?.let { context -> ClientAdapter(context, clients) }!!
        client_list_rv.adapter = adapter
        adapter.onItemClickListener = { client ->
            doInItemClickListener(client)
        }
        adapter.onItemLongClickListener = { client ->
            viewModel.remove(client,
                inFailureCase = {
                    activity?.runOnUiThread {
                        val baseDialog = BaseDialog(requireContext())
                        baseDialog.showErrorRemoveDialog()
                    }
                }, inSuccessCase = {
                    activity?.runOnUiThread {
                        showSnackBar(client, "removed")
                    }
                })
        }
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Client>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            client_list_animation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(client_list_animation) {
            scaleX = 0.5f
            scaleY = 0.5f
            visibility = VISIBLE
            playAnimation()
        }
    }

    private fun callInsertDialog() {
        context?.let { context ->
            ClientFormInsertDialog(view as ViewGroup, context)
                .initClientFormDialog { clientReturned ->
                    viewModel.insert(clientReturned)
                    showSnackBar(clientReturned, "saved")
                }
        }
    }

    protected fun showSnackBar(client: Client, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${client.name} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    abstract fun doInItemClickListener(client: Client)
    abstract fun doInOnCreateView()
}