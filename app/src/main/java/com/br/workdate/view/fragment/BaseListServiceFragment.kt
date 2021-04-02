package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import com.br.workdate.R
import com.br.workdate.databinding.FragmentListServiceBinding
import com.br.workdate.extension.getWindow
import com.br.workdate.extension.showDialogMessage
import com.br.workdate.model.Service
import com.br.workdate.view.dialog.ServiceFormInsertDialog
import com.br.workdate.view.recyclerview.adapter.ServiceAdapter
import com.br.workdate.view.viewmodel.LoginViewModel
import com.br.workdate.view.viewmodel.ServiceViewModel
import com.br.workdate.view.viewmodel.TutorialOfListService
import com.google.android.material.snackbar.Snackbar
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseListServiceFragment : Fragment() {

    private val binding by viewBinding(FragmentListServiceBinding::bind)
    private lateinit var adapter: ServiceAdapter
    protected val viewModel: ServiceViewModel by viewModel()
    private val loginViewModel: LoginViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentListServiceBinding.inflate(inflater).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.serviceListAnimation.setAnimation("anim/list_empty.json")
        checkIsFirstTimeInApp(view)

        binding.newService.setOnClickListener {
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
        binding.serviceListRv.adapter = adapter
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
            binding.serviceListAnimation.visibility = GONE
        }
    }

    private fun initAnimation() {
        with(binding.serviceListAnimation) {
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
            showDialogMessage(
                getString(R.string.error),
                messageError,
                requireContext()
            )
        }
    }

    private fun checkIsFirstTimeInApp(view: View) {
        if (loginViewModel.firstTimeInScreen(Constant.TITLE)) {
            val (width: Int, height: Int) = getWindow(activity)
            loginViewModel.initTutorial(TutorialOfListService(), activity, view, width, height)
        }
    }

    protected fun showSnackBar(service: Service, msg: String) {
        view?.let { view ->
            Snackbar.make(view, "${service.description} " + msg, Snackbar.LENGTH_SHORT).show()
        }
    }

    private object Constant {
        const val TITLE = "SERVICE_SCREEN"
    }

    abstract fun doInItemClickListener(service: Service)
}
