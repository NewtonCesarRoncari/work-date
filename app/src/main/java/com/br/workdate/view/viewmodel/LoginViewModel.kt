package com.br.workdate.view.viewmodel

import androidx.lifecycle.ViewModel
import com.br.workdate.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository) : ViewModel() {

    fun login() = repository.login()

    fun isLogged(): Boolean = repository.isLogged()
}