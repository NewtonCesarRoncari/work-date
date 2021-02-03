package com.br.workdate.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.br.workdate.R
import com.br.workdate.view.dialog.FilterDialog
import com.br.workdate.view.tabs.adapter.TabsAdapter
import com.br.workdate.view.viewmodel.FilterOfRelease
import com.br.workdate.view.viewmodel.FilterViewModel
import com.br.workdate.view.viewmodel.ReleaseViewModel
import kotlinx.android.synthetic.main.fragment_list_release.*
import org.koin.android.viewmodel.ext.android.viewModel

class ListReleaseFragment : Fragment() {

    private val viewModel: ReleaseViewModel by viewModel()
    private val filterViewModel: FilterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_release, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ttb = AnimationUtils.loadAnimation(context, R.anim.ttb)
        val cardView = resume_cardView
        cardView.startAnimation(ttb)

        initResume()
        initResumeInFilter()
        initTabLayout(view)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.base_filter_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_filter) {
            initFilterDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initResume() {
        lateinit var resumeRelease: ResumeReleaseView
        val view = activity?.window?.decorView
        viewModel.listAll().observe(viewLifecycleOwner, { releases ->
            resumeRelease = view?.let { view -> ResumeReleaseView(view, releases) }!!
            resumeRelease.update()
        })
    }

    private fun initResumeInFilter() {
        lateinit var resumeRelease: ResumeReleaseView
        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, { releases ->
            if (releases != null) {
                resumeRelease = view?.let { view -> ResumeReleaseView(view, releases) }!!
                resumeRelease.update()
            }
        })
    }

    private fun initTabLayout(view: View) {
        val tabsAdapter = context?.let { TabsAdapter(requireActivity().supportFragmentManager, it) }
        val viewPager by lazy {
            view.findViewById<ViewPager>(R.id.fragment_release_viewpager)
        }

        viewPager.adapter = tabsAdapter
        fragment_release_tabLayout.setupWithViewPager(viewPager)
    }

    private fun initFilterDialog() {
        context?.let { context ->
            activity?.let { activity ->
                FilterDialog(
                    context,
                    activity,
                    FilterOfRelease(),
                    loadClientNames = { clientAutoComplete ->
                        loadClientNames(context, clientAutoComplete)
                    },
                    loadServiceDescriptions = { serviceAutoComplete ->
                        loadServiceDescriptions(context, serviceAutoComplete)
                    },
                    returnQuery = { query -> viewModel.findReleaseFilter(query) }
                ).showFilterDialog()
            }
        }
    }

    private fun loadServiceDescriptions(
        context: Context,
        serviceAutoComplete: AutoCompleteTextView
    ) {
        filterViewModel.returnAllServicesDescriptions()
            .observe(viewLifecycleOwner, { descriptions ->
                val serviceAdapter = ArrayAdapter(
                    context,
                    R.layout.support_simple_spinner_dropdown_item,
                    descriptions
                )
                serviceAutoComplete.setAdapter(serviceAdapter)
            })
    }

    private fun loadClientNames(
        context: Context,
        clientAutoComplete: AutoCompleteTextView
    ) {
        filterViewModel.returnAllClientNames()
            .observe(viewLifecycleOwner, { names ->
                val clientAdapter = ArrayAdapter(
                    context,
                    R.layout.support_simple_spinner_dropdown_item,
                    names
                )
                clientAutoComplete.setAdapter(clientAdapter)
            })
    }
}