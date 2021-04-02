package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.br.workdate.R
import com.br.workdate.databinding.FragmentListClientBinding
import com.br.workdate.extension.getWindow
import com.br.workdate.extension.showDialogMessage
import com.br.workdate.model.Client
import com.br.workdate.view.dialog.ClientFormInsertDialog
import com.br.workdate.view.recyclerview.adapter.ClientAdapter
import com.br.workdate.view.viewmodel.ClientViewModel
import com.br.workdate.view.viewmodel.LoginViewModel
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import com.br.workdate.view.viewmodel.TutorialOfListClient
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import kotlinx.android.synthetic.main.fragment_list_client.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseListClientFragment : Fragment() {

    private val binding by viewBinding(FragmentListClientBinding::bind)
    protected val appComponentsViewModel: StateAppComponentsViewModel by sharedViewModel()
    private val loginViewModel: LoginViewModel by sharedViewModel()
    private lateinit var adapter: ClientAdapter
    protected val viewModel: ClientViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return FragmentListClientBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        client_list_animation.setAnimation("anim/list_empty.json")
        checkIsFirstTimeInApp(view)
        doInOnCreateView()

        binding.newClient.setOnClickListener {
            callInsertDialog()
        }
        viewModel.listAll().observe(viewLifecycleOwner, { clientList ->
            ifEmptyPlayAnimation(clientList)
            initClientAdapter(clientList)
        })
        setHasOptionsMenu(true)
    }

    private fun checkIsFirstTimeInApp(view: View) {
        if (loginViewModel.firstTimeInScreen(Constant.TITLE)) {
            val (width: Int, height: Int) = getWindow(activity)
            loginViewModel.initTutorial(TutorialOfListClient(), activity, view, width, height)
        }
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

    private fun initClientAdapter(clients: MutableList<Client>) {
        adapter = ClientAdapter(requireContext(), clients)
        binding.clientListRv.adapter = adapter
        adapter.onItemClickListener = { client ->
            doInItemClickListener(client)
        }
        adapter.onItemLongClickListener = { client ->
            viewModel.remove(client,
                inFailureCase = {
                    activity?.runOnUiThread {
                        showDialogMessage(
                            getString(R.string.error),
                            getString(R.string.message_linked_schedule),
                            requireContext()
                        )
                    }
                }, inSuccessCase = {
                    activity?.runOnUiThread {
                        showSnackBar(client, getString(R.string.removed))
                    }
                })
        }
        adapter.showMessageClientNoAddress = {
            showMessageErrorDialog(getString(R.string.message_empty_address))
        }
        adapter.showMessageClientNoPhone = {
            showMessageErrorDialog(getString(R.string.message_empty_phone))
        }
    }

    private fun showMessageErrorDialog(message: String) {
        showDialogMessage(
            getString(R.string.error),
            message,
            requireContext()
        )
    }

    private fun ifEmptyPlayAnimation(mutableList: MutableList<Client>) {
        if (mutableList.isEmpty()) {
            initAnimation()
        } else {
            binding.clientListAnimation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(binding.clientListAnimation) {
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
                    viewModel.insert(clientReturned,
                        inFailureCase = {
                            activity?.runOnUiThread {
                                showDialogMessage(
                                    getString(R.string.error),
                                    getString(R.string.message_client_name_already_exists),
                                    requireContext()
                                )
                            }
                        }, inSuccessCase = {
                            activity?.runOnUiThread {
                                showSnackBar(clientReturned, getString(R.string.saved))
                            }
                        })
                }
        }
    }

    protected fun showSnackBar(client: Client, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${client.name} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private object Constant {
        const val TITLE = "CLIENT_SCREEN"
    }

    abstract fun doInItemClickListener(client: Client)
    abstract fun doInOnCreateView()
}