package com.br.workdate.view.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.br.workdate.R
import com.br.workdate.view.dialog.FilterDialog
import com.br.workdate.view.tabs.adapter.TabsAdapter
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
        lateinit var resume: ResumeView
        val view = activity?.window?.decorView
        viewModel.listAll().observe(viewLifecycleOwner, Observer { releases ->
            resume = view?.let { view -> ResumeView(view, releases) }!!
            resume.update()
        })
    }

    private fun initResumeInFilter() {
        lateinit var resume: ResumeView
        viewModel.checkReleasesReturned()?.observe(viewLifecycleOwner, Observer { releases ->
            if (releases != null) {
                resume = view?.let { view -> ResumeView(view, releases) }!!
                resume.update()
            }
        })
    }

    private fun initTabLayout(view: View) {
        val tabsAdapter = TabsAdapter(requireActivity().supportFragmentManager)
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
                    "RELEASE",
                    loadClientNames = { clientAutoComplete ->
                        filterViewModel.returnAllClientNames()
                            .observe(viewLifecycleOwner, Observer { names ->
                                val clientAdapter = ArrayAdapter(
                                    context,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    names
                                )
                                clientAutoComplete.setAdapter(clientAdapter)
                            })
                    },
                    loadServiceDescriptions = { serviceAutoComplete ->
                        filterViewModel.returnAllServicesDescriptions()
                            .observe(viewLifecycleOwner, Observer { descriptions ->
                                val serviceAdapter = ArrayAdapter(
                                    context,
                                    R.layout.support_simple_spinner_dropdown_item,
                                    descriptions
                                )
                                serviceAutoComplete.setAdapter(serviceAdapter)
                            })
                    },
                    returnQuery = { query ->
                        viewModel.findReleaseFilter(query)
                    }
                ).showFilterDialog()
            }
        }
    }
}