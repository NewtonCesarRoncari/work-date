package com.br.workdate.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StateAppComponentsViewModel : ViewModel() {

    val components: LiveData<VisualComponents> get() = _components

    private var _components: MutableLiveData<VisualComponents> =
        MutableLiveData<VisualComponents>().also {
            it.value = havComponent
        }

    var havComponent: VisualComponents = VisualComponents()
        set(value) {
            field = value
            _components.value = value
        }
}

class VisualComponents(
    val bottomNavigation: Boolean = true
)