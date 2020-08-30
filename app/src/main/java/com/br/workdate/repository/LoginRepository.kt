package com.br.workdate.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_LOGIN = "LOGIN"

class LoginRepository(private val preferences: SharedPreferences) {

    fun login() {
        preferences.edit {
            putBoolean(KEY_LOGIN, true)
        }
    }

    fun isLogged(): Boolean = preferences.getBoolean(KEY_LOGIN, false)
}