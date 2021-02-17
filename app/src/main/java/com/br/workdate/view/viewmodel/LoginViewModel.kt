package com.br.workdate.view.viewmodel

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.br.workdate.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login() = repository.login()

    fun isLogged(): Boolean = repository.isLogged()

    fun firstTimeInScreen(screen: String): Boolean = repository.firstTimeInScreen(screen)

    fun initTutorial(
        tutorialOfScreen: TutorialOf,
        activity: FragmentActivity?,
        view: View,
        width: Int,
        height: Int
    ) {
        tutorialOfScreen.initTutorial(activity, view, width, height)
    }
}