package com.supertrain.navigator.nav

import androidx.lifecycle.ViewModel
import com.supertrain.navigator.UiActions
import com.supertrain.navigator.presentation.base.BaseScreen

class MainViewModel(
    private val uiActions: UiActions,
    private val navigator: MediateNavigator
) : ViewModel(), Navigator by navigator, UiActions by uiActions  {

    override fun onCleared() {
        super.onCleared()
        navigator.clear() // clear all activity actions

    }

}