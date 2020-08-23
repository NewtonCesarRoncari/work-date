package com.br.workdate.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.br.workdate.R
import com.br.workdate.view.viewmodel.StateAppComponentsViewModel
import kotlinx.android.synthetic.main.frame_navigation.*
import org.koin.android.viewmodel.ext.android.viewModel

class NavigationActivity : AppCompatActivity() {

    private val componentsViewModel: StateAppComponentsViewModel by viewModel()
    private val navController by lazy {
        Navigation.findNavController(this, R.id.frame_navigation)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_navigation)

        NavigationUI.setupWithNavController(bottom_nav, navController)

        navController.addOnDestinationChangedListener { _,
                                                        destination,
                                                        _ ->
            title = destination.label
        }

        componentsViewModel.components.observe(this, Observer {
            it?.also { havComponent ->
                run {
                    if (havComponent.bottomNavigation) {
                        bottom_nav.visibility = VISIBLE
                    } else {
                        bottom_nav.visibility = GONE
                    }
                }
            }
        })
    }
}